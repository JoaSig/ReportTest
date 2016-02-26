'use strict';

angular.module('documentmanagementApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pileDrilling', {
                parent: 'entity',
                url: '/pileDrillings',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'documentmanagementApp.pileDrilling.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pileDrilling/pileDrillings.html',
                        controller: 'PileDrillingController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pileDrilling');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pileDrilling.detail', {
                parent: 'entity',
                url: '/pileDrilling/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'documentmanagementApp.pileDrilling.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pileDrilling/pileDrilling-detail.html',
                        controller: 'PileDrillingDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pileDrilling');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PileDrilling', function($stateParams, PileDrilling) {
                        return PileDrilling.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pileDrilling.new', {
                parent: 'pileDrilling',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/pileDrilling/pileDrilling-dialog.html',
                        controller: 'PileDrillingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    drillingMachine: null,
                                    projectDepth: null,
                                    effectiveDepth: null,
                                    StartDate: null,
                                    EndDate: null,
                                    StartTime: null,
                                    EndTime: null,
                                    drillingId: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('pileDrilling', null, { reload: true });
                    }, function() {
                        $state.go('pileDrilling');
                    })
                }]
            })
            .state('pileDrilling.edit', {
                parent: 'pileDrilling',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/pileDrilling/pileDrilling-dialog.html',
                        controller: 'PileDrillingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PileDrilling', function(PileDrilling) {
                                return PileDrilling.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('pileDrilling', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('pileDrilling.delete', {
                parent: 'pileDrilling',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/pileDrilling/pileDrilling-delete-dialog.html',
                        controller: 'PileDrillingDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PileDrilling', function(PileDrilling) {
                                return PileDrilling.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('pileDrilling', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
