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
        $scope.datePickerForDrillingStartDate = {};

        $scope.datePickerForDrillingStartDate.status = {
            opened: false
        };

        $scope.datePickerForDrillingStartDateOpen = function($event) {
            $scope.datePickerForDrillingStartDate.status.opened = true;
        };
        $scope.datePickerForDrillingEndDate = {};

        $scope.datePickerForDrillingEndDate.status = {
            opened: false
        };

        $scope.datePickerForDrillingEndDateOpen = function($event) {
            $scope.datePickerForDrillingEndDate.status.opened = true;
        };
}]);
