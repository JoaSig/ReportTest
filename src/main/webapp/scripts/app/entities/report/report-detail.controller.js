'use strict';

angular.module('documentmanagementApp')
    .controller('ReportDetailController', function ($scope, $rootScope, $stateParams, entity, Report, User, Signature, GeneralInfo, ProjectInfo) {
        $scope.report = entity;
        $scope.load = function (id) {
            Report.get({id: id}, function(result) {
                $scope.report = result;
            });
        };
        var unsubscribe = $rootScope.$on('documentmanagementApp:reportUpdate', function(event, result) {
            $scope.report = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
