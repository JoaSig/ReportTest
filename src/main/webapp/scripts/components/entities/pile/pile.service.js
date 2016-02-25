'use strict';

angular.module('documentmanagementApp')
    .factory('Pile', function ($resource, DateUtils) {
        return $resource('api/piles/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createdAt = DateUtils.convertLocaleDateFromServer(data.createdAt);
                    data.lastUpdatedAt = DateUtils.convertLocaleDateFromServer(data.lastUpdatedAt);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.createdAt = DateUtils.convertLocaleDateToServer(data.createdAt);
                    data.lastUpdatedAt = DateUtils.convertLocaleDateToServer(data.lastUpdatedAt);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.createdAt = DateUtils.convertLocaleDateToServer(data.createdAt);
                    data.lastUpdatedAt = DateUtils.convertLocaleDateToServer(data.lastUpdatedAt);
                    return angular.toJson(data);
                }
            }
        });
    });
