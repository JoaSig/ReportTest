'use strict';

angular.module('documentmanagementApp')
    .factory('ProjectInfoSearch', function ($resource) {
        return $resource('api/_search/projectInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
