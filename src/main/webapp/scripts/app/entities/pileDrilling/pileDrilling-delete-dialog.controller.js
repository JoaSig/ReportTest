'use strict';

angular.module('documentmanagementApp')
	.controller('PileDrillingDeleteController', function($scope, $uibModalInstance, entity, PileDrilling) {

        $scope.pileDrilling = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PileDrilling.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
