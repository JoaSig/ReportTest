'use strict';

angular.module('documentmanagementApp')
	.controller('DrillingDeleteController', function($scope, $uibModalInstance, entity, Drilling) {

        $scope.drilling = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Drilling.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
