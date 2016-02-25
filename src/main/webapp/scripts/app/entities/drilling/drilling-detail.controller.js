'use strict';

angular.module('documentmanagementApp')
    .controller('DrillingDetailController', function ($scope, $rootScope, $stateParams, entity, Drilling, User, Pile) {
        $scope.drilling = entity;
        $scope.load = function (id) {
            Drilling.get({id: id}, function(result) {
                $scope.drilling = result;
            });
        };
        var unsubscribe = $rootScope.$on('documentmanagementApp:drillingUpdate', function(event, result) {
            $scope.drilling = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
