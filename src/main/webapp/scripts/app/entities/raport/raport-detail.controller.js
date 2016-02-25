'use strict';

angular.module('documentmanagementApp')
    .controller('RaportDetailController', function ($scope, $rootScope, $stateParams, entity, Raport, User, Signature, GeneralInfo, ProjectInfo) {
        $scope.raport = entity;
        $scope.load = function (id) {
            Raport.get({id: id}, function(result) {
                $scope.raport = result;
            });
        };
        var unsubscribe = $rootScope.$on('documentmanagementApp:raportUpdate', function(event, result) {
            $scope.raport = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
