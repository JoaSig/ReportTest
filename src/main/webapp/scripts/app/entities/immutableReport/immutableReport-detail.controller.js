'use strict';

angular.module('documentmanagementApp')
    .controller('ImmutableReportDetailController', function ($scope, $rootScope, $stateParams, entity, ImmutableReport, User, Signature, GeneralInfo, ProjectInfo) {
        $scope.immutableReport = entity;
        $scope.load = function (id) {
            ImmutableReport.get({id: id}, function(result) {
                $scope.immutableReport = result;
            });
        };
        var unsubscribe = $rootScope.$on('documentmanagementApp:immutableReportUpdate', function(event, result) {
            $scope.immutableReport = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
