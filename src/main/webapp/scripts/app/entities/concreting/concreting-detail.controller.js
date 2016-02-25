'use strict';

angular.module('documentmanagementApp')
    .controller('ConcretingDetailController', function ($scope, $rootScope, $stateParams, entity, Concreting, User, Pile, Item) {
        $scope.concreting = entity;
        $scope.load = function (id) {
            Concreting.get({id: id}, function(result) {
                $scope.concreting = result;
            });
        };
        var unsubscribe = $rootScope.$on('documentmanagementApp:concretingUpdate', function(event, result) {
            $scope.concreting = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
