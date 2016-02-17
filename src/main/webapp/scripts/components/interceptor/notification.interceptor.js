 'use strict';

angular.module('documentmanagementApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-documentmanagementApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-documentmanagementApp-params')});
                }
                return response;
            }
        };
    });
