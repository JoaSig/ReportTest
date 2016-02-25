'use strict';

angular.module('documentmanagementApp').controller('SteelCageDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'SteelCage', 'User', 'Pile',
        function($scope, $stateParams, $uibModalInstance, $q, entity, SteelCage, User, Pile) {

        $scope.steelCage = entity;
        $scope.users = User.query();
        $scope.piles = Pile.query();
        $scope.load = function(id) {
            SteelCage.get({id : id}, function(result) {
                $scope.steelCage = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('documentmanagementApp:steelCageUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.steelCage.id != null) {
                SteelCage.update($scope.steelCage, onSaveSuccess, onSaveError);
            } else {
                SteelCage.save($scope.steelCage, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForCreatedAt = {};

        $scope.datePickerForCreatedAt.status = {
            opened: false
        };

        $scope.datePickerForCreatedAtOpen = function($event) {
            $scope.datePickerForCreatedAt.status.opened = true;
        };
        $scope.datePickerForLastUpdatedAt = {};

        $scope.datePickerForLastUpdatedAt.status = {
            opened: false
        };

        $scope.datePickerForLastUpdatedAtOpen = function($event) {
            $scope.datePickerForLastUpdatedAt.status.opened = true;
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
