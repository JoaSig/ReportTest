'use strict';

angular.module('documentmanagementApp')
    .factory('ImmutableReportSearch', function ($resource) {
        return $resource('api/_search/immutableReports/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
