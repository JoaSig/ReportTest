'use strict';

angular.module('documentmanagementApp')
    .factory('PileDrillingSearch', function ($resource) {
        return $resource('api/_search/pileDrillings/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
