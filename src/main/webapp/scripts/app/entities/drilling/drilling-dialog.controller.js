'use strict';

angular.module('documentmanagementApp').controller('DrillingDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Drilling', 'User', 'Pile',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Drilling, User, Pile) {

        $scope.drilling = entity;
        $scope.users = User.query();
        $scope.piles = Pile.query();
        $scope.load = function(id) {
            Drilling.get({id : id}, function(result) {
                $scope.drilling = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('documentmanagementApp:drillingUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.drilling.id != null) {
                Drilling.update($scope.drilling, onSaveSuccess, onSaveError);
            } else {
                Drilling.save($scope.drilling, onSaveSuccess, onSaveError);
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
