'use strict';

angular.module('documentmanagementApp')
    .controller('GeneralInfoDetailController', function ($scope, $rootScope, $stateParams, entity, GeneralInfo) {
        $scope.generalInfo = entity;
        $scope.load = function (id) {
            GeneralInfo.get({id: id}, function(result) {
                $scope.generalInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('documentmanagementApp:generalInfoUpdate', function(event, result) {
            $scope.generalInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
