'use strict';

angular.module('documentmanagementApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pile', {
                parent: 'entity',
                url: '/piles',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'documentmanagementApp.pile.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pile/piles.html',
                        controller: 'PileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pile');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pile.detail', {
                parent: 'entity',
                url: '/pile/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'documentmanagementApp.pile.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pile/pile-detail.html',
                        controller: 'PileDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pile');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Pile', function($stateParams, Pile) {
                        return Pile.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pile.new', {
                parent: 'pile',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/pile/pile-dialog.html',
                        controller: 'PileDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    createdAt: null,
                                    lastUpdatedAt: null,
                                    lastUpdatedBy: null,
                                    nextPile: null,
                                    prevPile: null,
                                    comment: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('pile', null, { reload: true });
                    }, function() {
                        $state.go('pile');
                    })
                }]
            })
            .state('pile.edit', {
                parent: 'pile',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/pile/pile-dialog.html',
                        controller: 'PileDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Pile', function(Pile) {
                                return Pile.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('pile', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('pile.delete', {
                parent: 'pile',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/pile/pile-delete-dialog.html',
                        controller: 'PileDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Pile', function(Pile) {
                                return Pile.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('pile', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
