'use strict';

angular.module('documentmanagementApp')
    .factory('PileDrilling', function ($resource, DateUtils) {
        return $resource('api/pileDrillings/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'machine': { method: 'GET', isArray: false, url: '/api/pileDrillings/machine/:drillingMachine'},
            'machines': { method: 'GET', isArray: true, url: '/api/util/machine'},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    console.log('data.drillingStartTime: ' + data.drillingStartTime);
                    data.drillingStartTime = DateUtils.convertLocaleDateFromServer(data.drillingStartTime);
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
