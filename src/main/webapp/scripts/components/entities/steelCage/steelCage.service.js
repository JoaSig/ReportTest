'use strict';

angular.module('documentmanagementApp')
    .factory('SteelCage', function ($resource, DateUtils) {
        return $resource('api/steelCages/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createdAt = DateUtils.convertLocaleDateFromServer(data.createdAt);
                    data.lastUpdatedAt = DateUtils.convertLocaleDateFromServer(data.lastUpdatedAt);
                    data.startTime = DateUtils.convertLocaleDateFromServer(data.startTime);
                    data.endTime = DateUtils.convertLocaleDateFromServer(data.endTime);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.createdAt = DateUtils.convertLocaleDateToServer(data.createdAt);
                    data.lastUpdatedAt = DateUtils.convertLocaleDateToServer(data.lastUpdatedAt);
                    data.startTime = DateUtils.convertLocaleDateToServer(data.startTime);
                    data.endTime = DateUtils.convertLocaleDateToServer(data.endTime);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.createdAt = DateUtils.convertLocaleDateToServer(data.createdAt);
                    data.lastUpdatedAt = DateUtils.convertLocaleDateToServer(data.lastUpdatedAt);
                    data.startTime = DateUtils.convertLocaleDateToServer(data.startTime);
                    data.endTime = DateUtils.convertLocaleDateToServer(data.endTime);
                    return angular.toJson(data);
                }
            }
        });
    });
