'use strict';

angular.module('documentmanagementApp')
    .factory('Pile', function ($resource, DateUtils) {
        return $resource('api/piles/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.signatureDate = DateUtils.convertDateTimeFromServer(data.signatureDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
