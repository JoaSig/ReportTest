'use strict';

angular.module('documentmanagementApp').controller('ItemDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Item', 'Concreting',
        function($scope, $stateParams, $uibModalInstance, entity, Item, Concreting) {

        $scope.item = entity;
        $scope.concretings = Concreting.query();
        $scope.load = function(id) {
            Item.get({id : id}, function(result) {
                $scope.item = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('documentmanagementApp:itemUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.item.id != null) {
                Item.update($scope.item, onSaveSuccess, onSaveError);
            } else {
                Item.save($scope.item, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForOrderTime = {};

        $scope.datePickerForOrderTime.status = {
            opened: false
        };

        $scope.datePickerForOrderTimeOpen = function($event) {
            $scope.datePickerForOrderTime.status.opened = true;
        };
        $scope.datePickerForArrivalTime = {};

        $scope.datePickerForArrivalTime.status = {
            opened: false
        };

        $scope.datePickerForArrivalTimeOpen = function($event) {
            $scope.datePickerForArrivalTime.status.opened = true;
        };
}]);
