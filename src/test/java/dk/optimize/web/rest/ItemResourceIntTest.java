package dk.optimize.web.rest;

import dk.optimize.Application;
import dk.optimize.domain.Item;
import dk.optimize.repository.ItemRepository;
import dk.optimize.repository.search.ItemSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ItemResource REST controller.
 *
 * @see ItemResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ItemResourceIntTest {


    private static final LocalDate DEFAULT_ORDER_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORDER_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ARRIVAL_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ARRIVAL_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_TRUCK = 1;
    private static final Integer UPDATED_TRUCK = 2;

    private static final BigDecimal DEFAULT_CASTED = new BigDecimal(1);
    private static final BigDecimal UPDATED_CASTED = new BigDecimal(2);

    private static final Integer DEFAULT_SLUMP = 1;
    private static final Integer UPDATED_SLUMP = 2;

    private static final BigDecimal DEFAULT_THEORETICAL_CONCRETE_VOLUME = new BigDecimal(1);
    private static final BigDecimal UPDATED_THEORETICAL_CONCRETE_VOLUME = new BigDecimal(2);

    @Inject
    private ItemRepository itemRepository;

    @Inject
    private ItemSearchRepository itemSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restItemMockMvc;

    private Item item;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ItemResource itemResource = new ItemResource();
        ReflectionTestUtils.setField(itemResource, "itemSearchRepository", itemSearchRepository);
        ReflectionTestUtils.setField(itemResource, "itemRepository", itemRepository);
        this.restItemMockMvc = MockMvcBuilders.standaloneSetup(itemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        item = new Item();
        item.setOrderTime(DEFAULT_ORDER_TIME);
        item.setArrivalTime(DEFAULT_ARRIVAL_TIME);
        item.setTruck(DEFAULT_TRUCK);
        item.setCasted(DEFAULT_CASTED);
        item.setSlump(DEFAULT_SLUMP);
        item.setTheoreticalConcreteVolume(DEFAULT_THEORETICAL_CONCRETE_VOLUME);
    }

    @Test
    @Transactional
    public void createItem() throws Exception {
        int databaseSizeBeforeCreate = itemRepository.findAll().size();

        // Create the Item

        restItemMockMvc.perform(post("/api/items")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(item)))
                .andExpect(status().isCreated());

        // Validate the Item in the database
        List<Item> items = itemRepository.findAll();
        assertThat(items).hasSize(databaseSizeBeforeCreate + 1);
        Item testItem = items.get(items.size() - 1);
        assertThat(testItem.getOrderTime()).isEqualTo(DEFAULT_ORDER_TIME);
        assertThat(testItem.getArrivalTime()).isEqualTo(DEFAULT_ARRIVAL_TIME);
        assertThat(testItem.getTruck()).isEqualTo(DEFAULT_TRUCK);
        assertThat(testItem.getCasted()).isEqualTo(DEFAULT_CASTED);
        assertThat(testItem.getSlump()).isEqualTo(DEFAULT_SLUMP);
        assertThat(testItem.getTheoreticalConcreteVolume()).isEqualTo(DEFAULT_THEORETICAL_CONCRETE_VOLUME);
    }

    @Test
    @Transactional
    public void getAllItems() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the items
        restItemMockMvc.perform(get("/api/items?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(item.getId().intValue())))
                .andExpect(jsonPath("$.[*].orderTime").value(hasItem(DEFAULT_ORDER_TIME.toString())))
                .andExpect(jsonPath("$.[*].arrivalTime").value(hasItem(DEFAULT_ARRIVAL_TIME.toString())))
                .andExpect(jsonPath("$.[*].truck").value(hasItem(DEFAULT_TRUCK)))
                .andExpect(jsonPath("$.[*].casted").value(hasItem(DEFAULT_CASTED.intValue())))
                .andExpect(jsonPath("$.[*].slump").value(hasItem(DEFAULT_SLUMP)))
                .andExpect(jsonPath("$.[*].theoreticalConcreteVolume").value(hasItem(DEFAULT_THEORETICAL_CONCRETE_VOLUME.intValue())));
    }

    @Test
    @Transactional
    public void getItem() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get the item
        restItemMockMvc.perform(get("/api/items/{id}", item.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(item.getId().intValue()))
            .andExpect(jsonPath("$.orderTime").value(DEFAULT_ORDER_TIME.toString()))
            .andExpect(jsonPath("$.arrivalTime").value(DEFAULT_ARRIVAL_TIME.toString()))
            .andExpect(jsonPath("$.truck").value(DEFAULT_TRUCK))
            .andExpect(jsonPath("$.casted").value(DEFAULT_CASTED.intValue()))
            .andExpect(jsonPath("$.slump").value(DEFAULT_SLUMP))
            .andExpect(jsonPath("$.theoreticalConcreteVolume").value(DEFAULT_THEORETICAL_CONCRETE_VOLUME.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingItem() throws Exception {
        // Get the item
        restItemMockMvc.perform(get("/api/items/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItem() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

		int databaseSizeBeforeUpdate = itemRepository.findAll().size();

        // Update the item
        item.setOrderTime(UPDATED_ORDER_TIME);
        item.setArrivalTime(UPDATED_ARRIVAL_TIME);
        item.setTruck(UPDATED_TRUCK);
        item.setCasted(UPDATED_CASTED);
        item.setSlump(UPDATED_SLUMP);
        item.setTheoreticalConcreteVolume(UPDATED_THEORETICAL_CONCRETE_VOLUME);

        restItemMockMvc.perform(put("/api/items")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(item)))
                .andExpect(status().isOk());

        // Validate the Item in the database
        List<Item> items = itemRepository.findAll();
        assertThat(items).hasSize(databaseSizeBeforeUpdate);
        Item testItem = items.get(items.size() - 1);
        assertThat(testItem.getOrderTime()).isEqualTo(UPDATED_ORDER_TIME);
        assertThat(testItem.getArrivalTime()).isEqualTo(UPDATED_ARRIVAL_TIME);
        assertThat(testItem.getTruck()).isEqualTo(UPDATED_TRUCK);
        assertThat(testItem.getCasted()).isEqualTo(UPDATED_CASTED);
        assertThat(testItem.getSlump()).isEqualTo(UPDATED_SLUMP);
        assertThat(testItem.getTheoreticalConcreteVolume()).isEqualTo(UPDATED_THEORETICAL_CONCRETE_VOLUME);
    }

    @Test
    @Transactional
    public void deleteItem() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

		int databaseSizeBeforeDelete = itemRepository.findAll().size();

        // Get the item
        restItemMockMvc.perform(delete("/api/items/{id}", item.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Item> items = itemRepository.findAll();
        assertThat(items).hasSize(databaseSizeBeforeDelete - 1);
    }
}
