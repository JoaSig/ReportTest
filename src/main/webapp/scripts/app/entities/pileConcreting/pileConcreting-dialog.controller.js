'use strict';

angular.module('documentmanagementApp').controller('PileConcretingDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'PileConcreting', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, PileConcreting, User) {

        $scope.pileConcreting = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            PileConcreting.get({id : id}, function(result) {
                $scope.pileConcreting = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('documentmanagementApp:pileConcretingUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.pileConcreting.id != null) {
                PileConcreting.update($scope.pileConcreting, onSaveSuccess, onSaveError);
            } else {
                PileConcreting.save($scope.pileConcreting, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForConcretingDateStart = {};

        $scope.datePickerForConcretingDateStart.status = {
            opened: false
        };

        $scope.datePickerForConcretingDateStartOpen = function($event) {
            $scope.datePickerForConcretingDateStart.status.opened = true;
        };
}]);
