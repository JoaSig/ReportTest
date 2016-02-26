'use strict';

angular.module('documentmanagementApp').controller('PileDrillingDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'PileDrilling', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, PileDrilling, User) {

        $scope.pileDrilling = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            PileDrilling.get({id : id}, function(result) {
                $scope.pileDrilling = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('documentmanagementApp:pileDrillingUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.pileDrilling.id != null) {
                PileDrilling.update($scope.pileDrilling, onSaveSuccess, onSaveError);
            } else {
                PileDrilling.save($scope.pileDrilling, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForStartDate = {};

        $scope.datePickerForStartDate.status = {
            opened: false
        };

        $scope.datePickerForStartDateOpen = function($event) {
            $scope.datePickerForStartDate.status.opened = true;
        };
        $scope.datePickerForEndDate = {};

        $scope.datePickerForEndDate.status = {
            opened: false
        };

        $scope.datePickerForEndDateOpen = function($event) {
            $scope.datePickerForEndDate.status.opened = true;
        };
        $scope.datePickerForStartTime = {};

        $scope.datePickerForStartTime.status = {
            opened: false
        };

        $scope.datePickerForStartTimeOpen = function($event) {
            $scope.datePickerForStartTime.status.opened = true;
        };
        $scope.datePickerForEndTime = {};

        $scope.datePickerForEndTime.status = {
            opened: false
        };

        $scope.datePickerForEndTimeOpen = function($event) {
            $scope.datePickerForEndTime.status.opened = true;
        };
}]);
