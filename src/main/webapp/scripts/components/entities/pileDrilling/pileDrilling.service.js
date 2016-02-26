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
                    data.StartDate = DateUtils.convertLocaleDateFromServer(data.StartDate);
                    data.EndDate = DateUtils.convertLocaleDateFromServer(data.EndDate);
                    data.StartTime = DateUtils.convertLocaleDateFromServer(data.StartTime);
                    data.EndTime = DateUtils.convertLocaleDateFromServer(data.EndTime);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.StartDate = DateUtils.convertLocaleDateToServer(data.StartDate);
                    data.EndDate = DateUtils.convertLocaleDateToServer(data.EndDate);
                    data.StartTime = DateUtils.convertLocaleDateToServer(data.StartTime);
                    data.EndTime = DateUtils.convertLocaleDateToServer(data.EndTime);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.StartDate = DateUtils.convertLocaleDateToServer(data.StartDate);
                    data.EndDate = DateUtils.convertLocaleDateToServer(data.EndDate);
                    data.StartTime = DateUtils.convertLocaleDateToServer(data.StartTime);
                    data.EndTime = DateUtils.convertLocaleDateToServer(data.EndTime);
                    return angular.toJson(data);
                }
            }
        });
    });
