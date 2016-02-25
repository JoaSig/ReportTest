'use strict';

angular.module('documentmanagementApp')
    .factory('DrillingSearch', function ($resource) {
        return $resource('api/_search/drillings/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
