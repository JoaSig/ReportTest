'use strict';

angular.module('documentmanagementApp')
    .controller('SteelCageDetailController', function ($scope, $rootScope, $stateParams, entity, SteelCage, User, Pile) {
        $scope.steelCage = entity;
        $scope.load = function (id) {
            SteelCage.get({id: id}, function(result) {
                $scope.steelCage = result;
            });
        };
        var unsubscribe = $rootScope.$on('documentmanagementApp:steelCageUpdate', function(event, result) {
            $scope.steelCage = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
