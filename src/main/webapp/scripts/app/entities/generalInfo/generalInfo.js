'use strict';

angular.module('documentmanagementApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('generalInfo', {
                parent: 'entity',
                url: '/generalInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'documentmanagementApp.generalInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/generalInfo/generalInfos.html',
                        controller: 'GeneralInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('generalInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('generalInfo.detail', {
                parent: 'entity',
                url: '/generalInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'documentmanagementApp.generalInfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/generalInfo/generalInfo-detail.html',
                        controller: 'GeneralInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('generalInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'GeneralInfo', function($stateParams, GeneralInfo) {
                        return GeneralInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('generalInfo.new', {
                parent: 'generalInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/generalInfo/generalInfo-dialog.html',
                        controller: 'GeneralInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    treviRefNr: null,
                                    pilingRigType: null,
                                    machineSerialNr: null,
                                    date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('generalInfo', null, { reload: true });
                    }, function() {
                        $state.go('generalInfo');
                    })
                }]
            })
            .state('generalInfo.edit', {
                parent: 'generalInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/generalInfo/generalInfo-dialog.html',
                        controller: 'GeneralInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['GeneralInfo', function(GeneralInfo) {
                                return GeneralInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('generalInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('generalInfo.delete', {
                parent: 'generalInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/generalInfo/generalInfo-delete-dialog.html',
                        controller: 'GeneralInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['GeneralInfo', function(GeneralInfo) {
                                return GeneralInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('generalInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
