'use strict';

angular.module('documentmanagementApp')
    .factory('PileDrilling', function ($resource, DateUtils) {
        return $resource('api/pileDrillings/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'machine': { method: 'GET', isArray: true, url: '/api/pileDrillings/machine/:drillingMachine'},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.drillingStartDate = DateUtils.convertLocaleDateFromServer(data.drillingStartDate);
                    data.drillingEndDate = DateUtils.convertLocaleDateFromServer(data.drillingEndDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.drillingStartDate = DateUtils.convertLocaleDateToServer(data.drillingStartDate);
                    data.drillingEndDate = DateUtils.convertLocaleDateToServer(data.drillingEndDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.drillingStartDate = DateUtils.convertLocaleDateToServer(data.drillingStartDate);
                    data.drillingEndDate = DateUtils.convertLocaleDateToServer(data.drillingEndDate);
                    return angular.toJson(data);
                }
            }
        });
    });
