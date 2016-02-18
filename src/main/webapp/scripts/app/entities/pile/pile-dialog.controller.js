'use strict';

angular.module('documentmanagementApp').controller('PileDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Pile', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Pile, User) {

        $scope.pile = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Pile.get({id : id}, function(result) {
                $scope.pile = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('documentmanagementApp:pileUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.pile.id != null) {
                Pile.update($scope.pile, onSaveSuccess, onSaveError);
            } else {
                Pile.save($scope.pile, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForSignatureDate = {};

        $scope.datePickerForSignatureDate.status = {
            opened: false
        };

        $scope.datePickerForSignatureDateOpen = function($event) {
            $scope.datePickerForSignatureDate.status.opened = true;
        };
}]);
