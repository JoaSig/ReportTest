'use strict';

angular.module('documentmanagementApp')
    .factory('GeneralInfoSearch', function ($resource) {
        return $resource('api/_search/generalInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
