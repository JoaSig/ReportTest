'use strict';

angular.module('documentmanagementApp')
    .controller('PileDrillingDetailController', function ($scope, $rootScope, $stateParams, entity, PileDrilling, User) {
        $scope.pileDrilling = entity;
        $scope.load = function (id) {
            PileDrilling.get({id: id}, function(result) {
                $scope.pileDrilling = result;
            });
        };
        var unsubscribe = $rootScope.$on('documentmanagementApp:pileDrillingUpdate', function(event, result) {
            $scope.pileDrilling = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
