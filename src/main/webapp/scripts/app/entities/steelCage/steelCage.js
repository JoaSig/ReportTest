'use strict';

angular.module('documentmanagementApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('steelCage', {
                parent: 'entity',
                url: '/steelCages',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'documentmanagementApp.steelCage.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/steelCage/steelCages.html',
                        controller: 'SteelCageController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('steelCage');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('steelCage.detail', {
                parent: 'entity',
                url: '/steelCage/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'documentmanagementApp.steelCage.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/steelCage/steelCage-detail.html',
                        controller: 'SteelCageDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('steelCage');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'SteelCage', function($stateParams, SteelCage) {
                        return SteelCage.get({id : $stateParams.id});
                    }]
                }
            })
            .state('steelCage.new', {
                parent: 'steelCage',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/steelCage/steelCage-dialog.html',
                        controller: 'SteelCageDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    createdAt: null,
                                    idCage: null,
                                    sonicPipesCompliance: null,
                                    waterAndCappingSonicPipeFilling: null,
                                    overlappingCompliance: null,
                                    spacerPositionCompliance: null,
                                    distanceBetweenCageTopAndGuideWallEdge: null,
                                    verticalityCompliance: null,
                                    lastUpdatedAt: null,
                                    lastUpdatedBy: null,
                                    startTime: null,
                                    endTime: null,
                                    comment: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('steelCage', null, { reload: true });
                    }, function() {
                        $state.go('steelCage');
                    })
                }]
            })
            .state('steelCage.edit', {
                parent: 'steelCage',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/steelCage/steelCage-dialog.html',
                        controller: 'SteelCageDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['SteelCage', function(SteelCage) {
                                return SteelCage.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('steelCage', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('steelCage.delete', {
                parent: 'steelCage',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/steelCage/steelCage-delete-dialog.html',
                        controller: 'SteelCageDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['SteelCage', function(SteelCage) {
                                return SteelCage.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('steelCage', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
