'use strict';

angular.module('documentmanagementApp')
	.controller('PileConcretingDeleteController', function($scope, $uibModalInstance, entity, PileConcreting) {

        $scope.pileConcreting = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PileConcreting.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
