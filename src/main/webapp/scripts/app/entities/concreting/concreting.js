'use strict';

angular.module('documentmanagementApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('concreting', {
                parent: 'entity',
                url: '/concretings',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'documentmanagementApp.concreting.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/concreting/concretings.html',
                        controller: 'ConcretingController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('concreting');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('concreting.detail', {
                parent: 'entity',
                url: '/concreting/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'documentmanagementApp.concreting.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/concreting/concreting-detail.html',
                        controller: 'ConcretingDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('concreting');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Concreting', function($stateParams, Concreting) {
                        return Concreting.get({id : $stateParams.id});
                    }]
                }
            })
            .state('concreting.new', {
                parent: 'concreting',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/concreting/concreting-dialog.html',
                        controller: 'ConcretingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    createdAt: null,
                                    lastUpdatedAt: null,
                                    lastUpdatedBy: null,
                                    mixDesign: null,
                                    slumpFlowTest: null,
                                    pouringRate: null,
                                    totalCastedVolume: null,
                                    overConsumption: null,
                                    comment: null,
                                    startDate: null,
                                    endDate: null,
                                    startTime: null,
                                    endTime: null,
                                    calculatedCumulativeCls: null,
                                    calculatedTheoreticCls: null,
                                    calculatedDifference: null,
                                    calculatedPercent: null,
                                    sentBack: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('concreting', null, { reload: true });
                    }, function() {
                        $state.go('concreting');
                    })
                }]
            })
            .state('concreting.edit', {
                parent: 'concreting',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/concreting/concreting-dialog.html',
                        controller: 'ConcretingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Concreting', function(Concreting) {
                                return Concreting.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('concreting', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('concreting.delete', {
                parent: 'concreting',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/concreting/concreting-delete-dialog.html',
                        controller: 'ConcretingDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Concreting', function(Concreting) {
                                return Concreting.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('concreting', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
