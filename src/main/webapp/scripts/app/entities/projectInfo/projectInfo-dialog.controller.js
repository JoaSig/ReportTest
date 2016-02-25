'use strict';

angular.module('documentmanagementApp').controller('ProjectInfoDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProjectInfo', 'User', 'Pile',
        function($scope, $stateParams, $uibModalInstance, entity, ProjectInfo, User, Pile) {

        $scope.projectInfo = entity;
        $scope.users = User.query();
        $scope.piles = Pile.query();
        $scope.load = function(id) {
            ProjectInfo.get({id : id}, function(result) {
                $scope.projectInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('documentmanagementApp:projectInfoUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.projectInfo.id != null) {
                ProjectInfo.update($scope.projectInfo, onSaveSuccess, onSaveError);
            } else {
                ProjectInfo.save($scope.projectInfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
