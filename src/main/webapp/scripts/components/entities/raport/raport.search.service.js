'use strict';

angular.module('documentmanagementApp')
    .factory('RaportSearch', function ($resource) {
        return $resource('api/_search/raports/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
