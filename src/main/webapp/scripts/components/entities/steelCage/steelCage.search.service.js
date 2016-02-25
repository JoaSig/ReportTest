'use strict';

angular.module('documentmanagementApp')
    .factory('SteelCageSearch', function ($resource) {
        return $resource('api/_search/steelCages/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
