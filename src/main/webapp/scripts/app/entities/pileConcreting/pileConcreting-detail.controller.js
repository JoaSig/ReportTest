'use strict';

angular.module('documentmanagementApp')
    .controller('PileConcretingDetailController', function ($scope, $rootScope, $stateParams, entity, PileConcreting, User) {
        $scope.pileConcreting = entity;
        $scope.load = function (id) {
            PileConcreting.get({id: id}, function(result) {
                $scope.pileConcreting = result;
            });
        };
        var unsubscribe = $rootScope.$on('documentmanagementApp:pileConcretingUpdate', function(event, result) {
            $scope.pileConcreting = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
