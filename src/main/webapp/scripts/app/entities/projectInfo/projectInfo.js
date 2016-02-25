'use strict';

angular.module('documentmanagementApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('projectInfo', {
                parent: 'entity',
                url: '/projectInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'documentmanagementApp.projectInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/projectInfo/projectInfos.html',
                        controller: 'ProjectInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('projectInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('projectInfo.detail', {
                parent: 'entity',
                url: '/projectInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'documentmanagementApp.projectInfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/projectInfo/projectInfo-detail.html',
                        controller: 'ProjectInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('projectInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ProjectInfo', function($stateParams, ProjectInfo) {
                        return ProjectInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('projectInfo.new', {
                parent: 'projectInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/projectInfo/projectInfo-dialog.html',
                        controller: 'ProjectInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    wirCode: null,
                                    phase: null,
                                    site: null,
                                    subContractor: null,
                                    recordSN: null,
                                    contractNr: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('projectInfo', null, { reload: true });
                    }, function() {
                        $state.go('projectInfo');
                    })
                }]
            })
            .state('projectInfo.edit', {
                parent: 'projectInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/projectInfo/projectInfo-dialog.html',
                        controller: 'ProjectInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ProjectInfo', function(ProjectInfo) {
                                return ProjectInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('projectInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('projectInfo.delete', {
                parent: 'projectInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/projectInfo/projectInfo-delete-dialog.html',
                        controller: 'ProjectInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ProjectInfo', function(ProjectInfo) {
                                return ProjectInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('projectInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
