'use strict';

angular.module('documentmanagementApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('raport', {
                parent: 'entity',
                url: '/raports',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'documentmanagementApp.raport.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/raport/raports.html',
                        controller: 'RaportController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('raport');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('raport.detail', {
                parent: 'entity',
                url: '/raport/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'documentmanagementApp.raport.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/raport/raport-detail.html',
                        controller: 'RaportDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('raport');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Raport', function($stateParams, Raport) {
                        return Raport.get({id : $stateParams.id});
                    }]
                }
            })
            .state('raport.new', {
                parent: 'raport',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/raport/raport-dialog.html',
                        controller: 'RaportDialogController',
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
                        $state.go('raport', null, { reload: true });
                    }, function() {
                        $state.go('raport');
                    })
                }]
            })
            .state('raport.edit', {
                parent: 'raport',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/raport/raport-dialog.html',
                        controller: 'RaportDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Raport', function(Raport) {
                                return Raport.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('raport', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('raport.delete', {
                parent: 'raport',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/raport/raport-delete-dialog.html',
                        controller: 'RaportDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Raport', function(Raport) {
                                return Raport.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('raport', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
