'use strict';

angular.module('documentmanagementApp')
	.controller('SignatureDeleteController', function($scope, $uibModalInstance, entity, Signature) {

        $scope.signature = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Signature.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
