'use strict';

angular.module('documentmanagementApp')
    .factory('SignatureSearch', function ($resource) {
        return $resource('api/_search/signatures/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
