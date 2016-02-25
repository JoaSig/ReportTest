'use strict';

angular.module('documentmanagementApp').controller('ConcretingDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Concreting', 'User', 'Pile', 'Item',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Concreting, User, Pile, Item) {

        $scope.concreting = entity;
        $scope.users = User.query();
        $scope.piles = Pile.query();
        $scope.items = Item.query();
        $scope.load = function(id) {
            Concreting.get({id : id}, function(result) {
                $scope.concreting = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('documentmanagementApp:concretingUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.concreting.id != null) {
                Concreting.update($scope.concreting, onSaveSuccess, onSaveError);
            } else {
                Concreting.save($scope.concreting, onSaveSuccess, onSaveError);
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
