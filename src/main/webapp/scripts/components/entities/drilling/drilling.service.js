'use strict';

angular.module('documentmanagementApp')
    .factory('Drilling', function ($resource, DateUtils) {
        return $resource('api/drillings/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.startDate = DateUtils.convertLocaleDateFromServer(data.startDate);
                    data.endDate = DateUtils.convertLocaleDateFromServer(data.endDate);
                    data.startTime = DateUtils.convertLocaleDateFromServer(data.startTime);
                    data.endTime = DateUtils.convertLocaleDateFromServer(data.endTime);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.startDate = DateUtils.convertLocaleDateToServer(data.startDate);
                    data.endDate = DateUtils.convertLocaleDateToServer(data.endDate);
                    data.startTime = DateUtils.convertLocaleDateToServer(data.startTime);
                    data.endTime = DateUtils.convertLocaleDateToServer(data.endTime);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.startDate = DateUtils.convertLocaleDateToServer(data.startDate);
                    data.endDate = DateUtils.convertLocaleDateToServer(data.endDate);
                    data.startTime = DateUtils.convertLocaleDateToServer(data.startTime);
                    data.endTime = DateUtils.convertLocaleDateToServer(data.endTime);
                    return angular.toJson(data);
                }
            }
        });
    });
