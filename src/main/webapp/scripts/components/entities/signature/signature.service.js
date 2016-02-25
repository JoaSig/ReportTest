'use strict';

angular.module('documentmanagementApp')
    .factory('Signature', function ($resource, DateUtils) {
        return $resource('api/signatures/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.signedAt = DateUtils.convertLocaleDateFromServer(data.signedAt);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.signedAt = DateUtils.convertLocaleDateToServer(data.signedAt);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.signedAt = DateUtils.convertLocaleDateToServer(data.signedAt);
                    return angular.toJson(data);
                }
            }
        });
    });
