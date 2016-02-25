'use strict';

angular.module('documentmanagementApp')
    .factory('Item', function ($resource, DateUtils) {
        return $resource('api/items/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.orderTime = DateUtils.convertLocaleDateFromServer(data.orderTime);
                    data.arrivalTime = DateUtils.convertLocaleDateFromServer(data.arrivalTime);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.orderTime = DateUtils.convertLocaleDateToServer(data.orderTime);
                    data.arrivalTime = DateUtils.convertLocaleDateToServer(data.arrivalTime);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.orderTime = DateUtils.convertLocaleDateToServer(data.orderTime);
                    data.arrivalTime = DateUtils.convertLocaleDateToServer(data.arrivalTime);
                    return angular.toJson(data);
                }
            }
        });
    });
