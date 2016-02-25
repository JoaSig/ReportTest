'use strict';

angular.module('documentmanagementApp')
	.controller('RaportDeleteController', function($scope, $uibModalInstance, entity, Raport) {

        $scope.raport = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Raport.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
