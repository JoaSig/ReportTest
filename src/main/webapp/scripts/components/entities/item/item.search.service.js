'use strict';

angular.module('documentmanagementApp')
    .factory('ItemSearch', function ($resource) {
        return $resource('api/_search/items/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
