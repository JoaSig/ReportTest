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
                    data.startDate = DateUtils.convertLocaleDateFromServer(data.startDate);
                    data.endDate = DateUtils.convertLocaleDateFromServer(data.endDate);
                    data.endTime = DateUtils.convertTimeFromServer(data.endTime);
                    data.startTime = DateUtils.convertTimeFromServer(data.startTime);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.startDate = DateUtils.convertLocaleDateToServer(data.startDate);
                    data.endDate = DateUtils.convertLocaleDateToServer(data.endDate);
                    data.startTime = DateUtils.convertTimeToServer(data.startTime);
                    data.endTime = DateUtils.convertTimeToServer(data.endTime);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.startDate = DateUtils.convertLocaleDateToServer(data.startDate);
                    data.endDate = DateUtils.convertLocaleDateToServer(data.endDate);
                    data.startTime = DateUtils.convertTimeToServer(data.startTime);
                    data.endTime = DateUtils.convertTimeToServer(data.endTime);
                    return angular.toJson(data);
                }
            }
        });
    });
