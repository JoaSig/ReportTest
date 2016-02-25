'use strict';

angular.module('documentmanagementApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('drilling', {
                parent: 'entity',
                url: '/drillings',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'documentmanagementApp.drilling.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/drilling/drillings.html',
                        controller: 'DrillingController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('drilling');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('drilling.detail', {
                parent: 'entity',
                url: '/drilling/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'documentmanagementApp.drilling.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/drilling/drilling-detail.html',
                        controller: 'DrillingDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('drilling');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Drilling', function($stateParams, Drilling) {
                        return Drilling.get({id : $stateParams.id});
                    }]
                }
            })
            .state('drilling.new', {
                parent: 'drilling',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/drilling/drilling-dialog.html',
                        controller: 'DrillingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    pileType: null,
                                    pilingRigType: null,
                                    pileNr: null,
                                    pileLength: null,
                                    diameter: null,
                                    topGuideElevation: null,
                                    pileTopLevel: null,
                                    pileCutoffElevation: null,
                                    pileToeLevel: null,
                                    casingDeviation: null,
                                    startDate: null,
                                    endDate: null,
                                    startTime: null,
                                    endTime: null,
                                    comment: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('drilling', null, { reload: true });
                    }, function() {
                        $state.go('drilling');
                    })
                }]
            })
            .state('drilling.edit', {
                parent: 'drilling',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/drilling/drilling-dialog.html',
                        controller: 'DrillingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Drilling', function(Drilling) {
                                return Drilling.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('drilling', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('drilling.delete', {
                parent: 'drilling',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/drilling/drilling-delete-dialog.html',
                        controller: 'DrillingDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Drilling', function(Drilling) {
                                return Drilling.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('drilling', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
