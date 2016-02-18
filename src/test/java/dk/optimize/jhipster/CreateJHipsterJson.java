package dk.optimize.jhipster;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.reflections.Reflections;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Date: 18/02/16
 */
public class CreateJHipsterJson {
    private static final String BASE_PACKAGE = "dk.optimize.domain.report";
    private static final List<String> unqualifiedEntities = new ArrayList<>();
    static {
        unqualifiedEntities.add("User");
        unqualifiedEntities.add("Role");
    }

    private static final List<String> renameEntities = new ArrayList<>();
    static {
        renameEntities.add("Configuration");
    }

    @Test
    public void createTheJSON() throws InstantiationException, IllegalAccessException, IOException {
        Map<String, JHipsterEntity> map = new LinkedHashMap<>();

        Reflections reflections = new Reflections(BASE_PACKAGE);
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(javax.persistence.Entity.class);
        for (Class<?> clazz : annotated) {
            String entityName = clazz.getSimpleName();
            if (unqualifiedEntities.contains(entityName)) {
                System.out.println(entityName + " - Cannot be converted");
                continue;
            }

            if (renameEntities.contains(entityName)) {
                entityName = "App" + clazz.getSimpleName();
                System.out.println(clazz.getSimpleName() + " - is renamed to: " + entityName);
            }

            JHipsterEntity entity = new JHipsterEntity();
            entity.changelogDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

            map.put(entityName, entity);

            List<Field> fields = getAllFields(clazz);

            int fieldId = 1;
            int relationshipId = 1;
            for (Field field : fields) {
                if (isEntityField(field)) {
                    JHipsterField jField = new JHipsterField();
                    jField.fieldId = fieldId++;
                    jField.fieldName = field.getName();
                    jField.fieldType = field.getType().getSimpleName();

                    if ("Date".equals(jField.fieldType)) {
                        jField.fieldType = "LocalDate";
                    }
                    entity.fields.add(jField);
                }

                if(isRelationshipField(field)) {
                    JHipsterRelationship relationship = new JHipsterRelationship();
                    relationship.relationshipId = relationshipId++;

                    RelationshipType type = getRelationshipAnnotation(field);


                    relationship.relationshipType = type.getJhipsterName();

                    Object fieldType = field.getGenericType();
                    if (fieldType instanceof Class) {
                        relationship.otherEntityName = getEntityName(((Class<?>)fieldType).getSimpleName());
                    } else {
                        ParameterizedType listType = (ParameterizedType)fieldType;
                        Type[] types = listType.getActualTypeArguments();
                        relationship.otherEntityName = getEntityName(((Class<?>)types[types.length-1]).getSimpleName());
                    }
                    relationship.relationshipName = relationship.otherEntityName;
                    relationship.otherEntityRelationshipName = getEntityName(entityName);

                    relationship.ownerSide = isOwner(field);

                    if (hasField(clazz, "name")) {
                        relationship.otherEntityField = "name";
                    } else if (hasField(clazz, "label")) {
                        relationship.otherEntityField = "label";
                    }

                    entity.relationships.add(relationship);
                }
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);


        List<Entry<String, JHipsterEntity>> sortedList = sortMap(new LinkedList<>(map.entrySet()));

        for (Entry<String, JHipsterEntity> entry : sortedList) {
            System.out.println("yo jhipster:entity " + entry.getKey());
            System.out.println("a");

            File f = new File("target/"+entry.getKey()+".json");
            FileWriter fileWriter = new FileWriter(f);
            String data = objectMapper.writeValueAsString(entry.getValue());
            System.out.println(data);
            IOUtils.write(data, fileWriter);
            IOUtils.closeQuietly(fileWriter);
        }

    }

    public static List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }

    private String getEntityName(String name) {
        return Character.toLowerCase(name.charAt(0)) + name.substring(1);
    }


    private static final List<String> relationShipAnnotations = new ArrayList<>();
    static {
        relationShipAnnotations.add(OneToMany.class.getName());
        relationShipAnnotations.add(ManyToOne.class.getName());
        relationShipAnnotations.add(ManyToMany.class.getName());
        relationShipAnnotations.add(OneToOne.class.getName());
    }

    private static final List<String> notBasicAnnotations = new ArrayList<>();
    static {
        notBasicAnnotations.addAll(relationShipAnnotations);
        notBasicAnnotations.add(Transient.class.getName());
        notBasicAnnotations.add(Id.class.getName());
    }

    private boolean isRelationshipField(Field field) {
        return getRelationshipAnnotation(field) != null;
    }

    public RelationshipType getRelationshipAnnotation(Field field) {
        Annotation[] annotations = field.getAnnotations();
        if (annotations != null && annotations.length > 0) {
            for (Annotation annotation : annotations) {
                String name = annotation.annotationType().getName();
                if (relationShipAnnotations.contains(name)) {
                    return RelationshipType.valueOf(annotation.annotationType().getSimpleName());
                }
            }
        }
        return null;
    }

    private boolean hasField(Class<?> clazz, String fieldName) {
        try {
            if (clazz.getDeclaredField(fieldName) != null) {
                return true;
            }
        } catch (NoSuchFieldException e) {
        } catch (SecurityException e) {
        }

        return false;
    }


    private static List<Entry<String, JHipsterEntity>> sortMap(List<Entry<String, JHipsterEntity>> list) {
        // Defined Custom Comparator here
        Collections.sort(list, (o1, o2) -> o1.getValue().isChildOf(o2.getKey()) ? 0 : -1);

        return list;
    }

    public enum RelationshipType {
        OneToMany("one-to-many"),
        OneToOne("one-to-one"),
        ManyToOne("many-to-one"),
        ManyToMany("many-to-many");

        RelationshipType(String name) {
            this.jhipsterName = name;
        }

        private String jhipsterName;

        public String getJhipsterName() {
            return jhipsterName;
        }
    }

    private boolean isOwner(Field field) {
        Annotation[] annotations = field.getAnnotations();
        if (annotations != null && annotations.length > 0) {
            for (Annotation annotation : annotations) {
                String name = annotation.annotationType().getSimpleName();
                if (name.equals("JoinTable")) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isEntityField(Field field) {
        if (field.getName().equals("serialVersionUID"))
            return false;

        if (Modifier.isTransient(field.getModifiers())
            || Modifier.isStatic(field.getModifiers())
            || Modifier.isFinal(field.getModifiers())
            ) {
            return false;
        }

        Annotation[] annotations = field.getAnnotations();
        if (annotations != null && annotations.length > 0) {
            for (Annotation annotation : annotations) {
                String name = annotation.annotationType().getName();
                if (notBasicAnnotations.contains(name)) {
                    return false;
                }
            }
        }

        return true;
    }

    public static class JHipsterEntity {
        public List<JHipsterRelationship> relationships = new ArrayList<>();
        public List<JHipsterField> fields = new ArrayList<>();
        public String changelogDate;
        public String dto = "no";
        public String pagination = "yes";

        public boolean isChildOf(String otherEnityName) {
            for (JHipsterRelationship relationship : relationships) {
                if (relationship.relationshipType.equals("many-to-one")
                    && relationship.otherEntityName.toLowerCase().equals(otherEnityName.toLowerCase())) {
                    return true;
                }
            }

            return false;
        }
    }

    public static class JHipsterField {
        public int fieldId;
        public String fieldName;
        public String fieldType;
        public List<String> fieldValidateRules = new ArrayList<>();
//        public String fieldValidateRulesMaxlength;
    }

    public static class JHipsterRelationship {
        public int relationshipId;
        public String relationshipName;
        public String relationshipType;
        public String otherEntityName;
        public String otherEntityRelationshipName;
        public String otherEntityField;
        public Boolean ownerSide;
    }
}
