'use strict';

angular.module('documentmanagementApp')
    .factory('ImmutableReport', function ($resource, DateUtils) {
        return $resource('api/immutableReports/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createdAt = DateUtils.convertLocaleDateFromServer(data.createdAt);
                    data.lastUpdatedAt = DateUtils.convertLocaleDateFromServer(data.lastUpdatedAt);
                    data.sendAt = DateUtils.convertLocaleDateFromServer(data.sendAt);
                    data.receivedAt = DateUtils.convertLocaleDateFromServer(data.receivedAt);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.createdAt = DateUtils.convertLocaleDateToServer(data.createdAt);
                    data.lastUpdatedAt = DateUtils.convertLocaleDateToServer(data.lastUpdatedAt);
                    data.sendAt = DateUtils.convertLocaleDateToServer(data.sendAt);
                    data.receivedAt = DateUtils.convertLocaleDateToServer(data.receivedAt);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.createdAt = DateUtils.convertLocaleDateToServer(data.createdAt);
                    data.lastUpdatedAt = DateUtils.convertLocaleDateToServer(data.lastUpdatedAt);
                    data.sendAt = DateUtils.convertLocaleDateToServer(data.sendAt);
                    data.receivedAt = DateUtils.convertLocaleDateToServer(data.receivedAt);
                    return angular.toJson(data);
                }
            }
        });
    });
