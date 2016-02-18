'use strict';

angular.module('documentmanagementApp')
    .factory('PileSearch', function ($resource) {
        return $resource('api/_search/piles/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
