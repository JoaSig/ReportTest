'use strict';

angular.module('documentmanagementApp')
	.controller('GeneralInfoDeleteController', function($scope, $uibModalInstance, entity, GeneralInfo) {

        $scope.generalInfo = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            GeneralInfo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
