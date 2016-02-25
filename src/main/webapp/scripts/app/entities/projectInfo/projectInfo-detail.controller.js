'use strict';

angular.module('documentmanagementApp')
    .controller('ProjectInfoDetailController', function ($scope, $rootScope, $stateParams, entity, ProjectInfo, User, Pile) {
        $scope.projectInfo = entity;
        $scope.load = function (id) {
            ProjectInfo.get({id: id}, function(result) {
                $scope.projectInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('documentmanagementApp:projectInfoUpdate', function(event, result) {
            $scope.projectInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
