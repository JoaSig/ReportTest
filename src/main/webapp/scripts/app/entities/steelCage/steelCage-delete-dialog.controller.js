'use strict';

angular.module('documentmanagementApp')
	.controller('SteelCageDeleteController', function($scope, $uibModalInstance, entity, SteelCage) {

        $scope.steelCage = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SteelCage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
