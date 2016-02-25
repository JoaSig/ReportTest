'use strict';

angular.module('documentmanagementApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('immutableReport', {
                parent: 'entity',
                url: '/immutableReports',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'documentmanagementApp.immutableReport.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/immutableReport/immutableReports.html',
                        controller: 'ImmutableReportController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('immutableReport');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('immutableReport.detail', {
                parent: 'entity',
                url: '/immutableReport/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'documentmanagementApp.immutableReport.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/immutableReport/immutableReport-detail.html',
                        controller: 'ImmutableReportDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('immutableReport');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ImmutableReport', function($stateParams, ImmutableReport) {
                        return ImmutableReport.get({id : $stateParams.id});
                    }]
                }
            })
            .state('immutableReport.new', {
                parent: 'immutableReport',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/immutableReport/immutableReport-dialog.html',
                        controller: 'ImmutableReportDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    createdAt: null,
                                    lastUpdatedAt: null,
                                    lastUpdatedBy: null,
                                    sendAt: null,
                                    receivedAt: null,
                                    description: null,
                                    comment: null,
                                    emails: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('immutableReport', null, { reload: true });
                    }, function() {
                        $state.go('immutableReport');
                    })
                }]
            })
            .state('immutableReport.edit', {
                parent: 'immutableReport',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/immutableReport/immutableReport-dialog.html',
                        controller: 'ImmutableReportDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ImmutableReport', function(ImmutableReport) {
                                return ImmutableReport.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('immutableReport', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('immutableReport.delete', {
                parent: 'immutableReport',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/immutableReport/immutableReport-delete-dialog.html',
                        controller: 'ImmutableReportDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ImmutableReport', function(ImmutableReport) {
                                return ImmutableReport.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('immutableReport', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
