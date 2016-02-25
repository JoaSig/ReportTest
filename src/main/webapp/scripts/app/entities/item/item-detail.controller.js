'use strict';

angular.module('documentmanagementApp')
    .controller('ItemDetailController', function ($scope, $rootScope, $stateParams, entity, Item, Concreting) {
        $scope.item = entity;
        $scope.load = function (id) {
            Item.get({id: id}, function(result) {
                $scope.item = result;
            });
        };
        var unsubscribe = $rootScope.$on('documentmanagementApp:itemUpdate', function(event, result) {
            $scope.item = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
