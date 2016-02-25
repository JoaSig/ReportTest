'use strict';

angular.module('documentmanagementApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('signature', {
                parent: 'entity',
                url: '/signatures',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'documentmanagementApp.signature.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/signature/signatures.html',
                        controller: 'SignatureController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('signature');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('signature.detail', {
                parent: 'entity',
                url: '/signature/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'documentmanagementApp.signature.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/signature/signature-detail.html',
                        controller: 'SignatureDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('signature');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Signature', function($stateParams, Signature) {
                        return Signature.get({id : $stateParams.id});
                    }]
                }
            })
            .state('signature.new', {
                parent: 'signature',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/signature/signature-dialog.html',
                        controller: 'SignatureDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    signedAt: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('signature', null, { reload: true });
                    }, function() {
                        $state.go('signature');
                    })
                }]
            })
            .state('signature.edit', {
                parent: 'signature',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/signature/signature-dialog.html',
                        controller: 'SignatureDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Signature', function(Signature) {
                                return Signature.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('signature', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('signature.delete', {
                parent: 'signature',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/signature/signature-delete-dialog.html',
                        controller: 'SignatureDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Signature', function(Signature) {
                                return Signature.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('signature', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
