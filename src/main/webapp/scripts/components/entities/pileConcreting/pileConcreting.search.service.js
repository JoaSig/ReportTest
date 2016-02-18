'use strict';

angular.module('documentmanagementApp')
    .factory('PileConcretingSearch', function ($resource) {
        return $resource('api/_search/pileConcretings/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
