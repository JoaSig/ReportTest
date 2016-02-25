'use strict';

angular.module('documentmanagementApp')
	.controller('ConcretingDeleteController', function($scope, $uibModalInstance, entity, Concreting) {

        $scope.concreting = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Concreting.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
