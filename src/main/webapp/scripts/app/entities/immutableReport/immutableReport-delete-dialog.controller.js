'use strict';

angular.module('documentmanagementApp')
	.controller('ImmutableReportDeleteController', function($scope, $uibModalInstance, entity, ImmutableReport) {

        $scope.immutableReport = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ImmutableReport.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
