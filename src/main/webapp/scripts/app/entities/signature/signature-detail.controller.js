'use strict';

angular.module('documentmanagementApp')
    .controller('SignatureDetailController', function ($scope, $rootScope, $stateParams, entity, Signature, User, Report) {
        $scope.signature = entity;
        $scope.load = function (id) {
            Signature.get({id: id}, function(result) {
                $scope.signature = result;
            });
        };
        var unsubscribe = $rootScope.$on('documentmanagementApp:signatureUpdate', function(event, result) {
            $scope.signature = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
