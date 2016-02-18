'use strict';

angular.module('documentmanagementApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pileConcreting', {
                parent: 'entity',
                url: '/pileConcretings',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'documentmanagementApp.pileConcreting.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pileConcreting/pileConcretings.html',
                        controller: 'PileConcretingController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pileConcreting');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pileConcreting.detail', {
                parent: 'entity',
                url: '/pileConcreting/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'documentmanagementApp.pileConcreting.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pileConcreting/pileConcreting-detail.html',
                        controller: 'PileConcretingDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pileConcreting');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PileConcreting', function($stateParams, PileConcreting) {
                        return PileConcreting.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pileConcreting.new', {
                parent: 'pileConcreting',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/pileConcreting/pileConcreting-dialog.html',
                        controller: 'PileConcretingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    mixDesign: null,
                                    slump1: null,
                                    slump2: null,
                                    slump3: null,
                                    slump4: null,
                                    slump5: null,
                                    truckId1: null,
                                    truckId2: null,
                                    truckId3: null,
                                    truckId4: null,
                                    truckId5: null,
                                    casted1: null,
                                    casted2: null,
                                    casted3: null,
                                    casted4: null,
                                    casted5: null,
                                    concretingDateStart: null,
                                    concretingStartTime: null,
                                    concretingEndTime: null,
                                    concretingOrderTime1: null,
                                    concretingArrivalTime1: null,
                                    concretingOrderTime2: null,
                                    concretingArrivalTime2: null,
                                    concretingOrderTime3: null,
                                    concretingArrivalTime3: null,
                                    concretingOrderTime4: null,
                                    concretingArrivalTime4: null,
                                    concretingOrderTime5: null,
                                    concretingArrivalTime5: null,
                                    calculatedCumulativeCls: null,
                                    calculatedTheoricCls: null,
                                    calculatedDifference: null,
                                    calculatedProcent: null,
                                    concreteSentBack: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('pileConcreting', null, { reload: true });
                    }, function() {
                        $state.go('pileConcreting');
                    })
                }]
            })
            .state('pileConcreting.edit', {
                parent: 'pileConcreting',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/pileConcreting/pileConcreting-dialog.html',
                        controller: 'PileConcretingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PileConcreting', function(PileConcreting) {
                                return PileConcreting.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('pileConcreting', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('pileConcreting.delete', {
                parent: 'pileConcreting',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/pileConcreting/pileConcreting-delete-dialog.html',
                        controller: 'PileConcretingDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PileConcreting', function(PileConcreting) {
                                return PileConcreting.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('pileConcreting', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
