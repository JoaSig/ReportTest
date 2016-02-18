'use strict';

angular.module('documentmanagementApp')
	.controller('PileDeleteController', function($scope, $uibModalInstance, entity, Pile) {

        $scope.pile = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Pile.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
