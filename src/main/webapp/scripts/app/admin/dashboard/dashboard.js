'use strict';

angular.module('documentmanagementApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('dashboard', {
                parent: 'admin',
                url: '/dashboard',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'dashboard.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/admin/dashboard/dashboard.html',
                        controller: 'DashboardController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dashboard');
                        return $translate.refresh();
                    }]
                }
            })
    });
