'use strict';

angular.module('documentmanagementApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


