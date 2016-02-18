'use strict';

angular.module('documentmanagementApp')
    .factory('PileConcreting', function ($resource, DateUtils) {
        return $resource('api/pileConcretings/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.concretingDateStart = DateUtils.convertLocaleDateFromServer(data.concretingDateStart);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.concretingDateStart = DateUtils.convertLocaleDateToServer(data.concretingDateStart);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.concretingDateStart = DateUtils.convertLocaleDateToServer(data.concretingDateStart);
                    return angular.toJson(data);
                }
            }
        });
    });
