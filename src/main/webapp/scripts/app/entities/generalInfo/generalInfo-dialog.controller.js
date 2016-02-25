'use strict';

angular.module('documentmanagementApp').controller('GeneralInfoDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'GeneralInfo',
        function($scope, $stateParams, $uibModalInstance, entity, GeneralInfo) {

        $scope.generalInfo = entity;
        $scope.load = function(id) {
            GeneralInfo.get({id : id}, function(result) {
                $scope.generalInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('documentmanagementApp:generalInfoUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.generalInfo.id != null) {
                GeneralInfo.update($scope.generalInfo, onSaveSuccess, onSaveError);
            } else {
                GeneralInfo.save($scope.generalInfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForDate = {};

        $scope.datePickerForDate.status = {
            opened: false
        };

        $scope.datePickerForDateOpen = function($event) {
            $scope.datePickerForDate.status.opened = true;
        };
}]);
