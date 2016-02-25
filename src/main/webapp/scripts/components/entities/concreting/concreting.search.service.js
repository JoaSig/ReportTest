'use strict';

angular.module('documentmanagementApp')
    .factory('ConcretingSearch', function ($resource) {
        return $resource('api/_search/concretings/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
