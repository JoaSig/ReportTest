'use strict';

angular.module('documentmanagementApp').controller('SignatureDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Signature', 'User', 'Report',
        function($scope, $stateParams, $uibModalInstance, entity, Signature, User, Report) {

        $scope.signature = entity;
        $scope.users = User.query();
        $scope.reports = Report.query();
        $scope.load = function(id) {
            Signature.get({id : id}, function(result) {
                $scope.signature = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('documentmanagementApp:signatureUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.signature.id != null) {
                Signature.update($scope.signature, onSaveSuccess, onSaveError);
            } else {
                Signature.save($scope.signature, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForSignedAt = {};

        $scope.datePickerForSignedAt.status = {
            opened: false
        };

        $scope.datePickerForSignedAtOpen = function($event) {
            $scope.datePickerForSignedAt.status.opened = true;
        };
}]);
