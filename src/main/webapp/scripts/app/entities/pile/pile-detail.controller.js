'use strict';

angular.module('documentmanagementApp')
    .controller('PileDetailController', function ($scope, $rootScope, $stateParams, entity, Pile, User) {
        $scope.pile = entity;
        $scope.load = function (id) {
            Pile.get({id: id}, function(result) {
                $scope.pile = result;
            });
        };
        var unsubscribe = $rootScope.$on('documentmanagementApp:pileUpdate', function(event, result) {
            $scope.pile = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
