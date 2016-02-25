'use strict';

angular.module('documentmanagementApp')
	.controller('ProjectInfoDeleteController', function($scope, $uibModalInstance, entity, ProjectInfo) {

        $scope.projectInfo = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ProjectInfo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
