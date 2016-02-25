'use strict';

angular.module('documentmanagementApp')
    .factory('Concreting', function ($resource, DateUtils) {
        return $resource('api/concretings/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createdAt = DateUtils.convertLocaleDateFromServer(data.createdAt);
                    data.lastUpdatedAt = DateUtils.convertLocaleDateFromServer(data.lastUpdatedAt);
                    data.startDate = DateUtils.convertLocaleDateFromServer(data.startDate);
                    data.endDate = DateUtils.convertLocaleDateFromServer(data.endDate);
                    data.startTime = DateUtils.convertDateTimeFromServer(data.startTime);
                    data.endTime = DateUtils.convertLocaleDateFromServer(data.endTime);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.createdAt = DateUtils.convertLocaleDateToServer(data.createdAt);
                    data.lastUpdatedAt = DateUtils.convertLocaleDateToServer(data.lastUpdatedAt);
                    data.startDate = DateUtils.convertLocaleDateToServer(data.startDate);
                    data.endDate = DateUtils.convertLocaleDateToServer(data.endDate);
                    data.endTime = DateUtils.convertLocaleDateToServer(data.endTime);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.createdAt = DateUtils.convertLocaleDateToServer(data.createdAt);
                    data.lastUpdatedAt = DateUtils.convertLocaleDateToServer(data.lastUpdatedAt);
                    data.startDate = DateUtils.convertLocaleDateToServer(data.startDate);
                    data.endDate = DateUtils.convertLocaleDateToServer(data.endDate);
                    data.endTime = DateUtils.convertLocaleDateToServer(data.endTime);
                    return angular.toJson(data);
                }
            }
        });
    });
