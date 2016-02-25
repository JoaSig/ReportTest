'use strict';

angular.module('documentmanagementApp').controller('RaportDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Raport', 'User', 'Signature', 'GeneralInfo', 'ProjectInfo',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Raport, User, Signature, GeneralInfo, ProjectInfo) {

        $scope.raport = entity;
        $scope.users = User.query();
        $scope.signatures = Signature.query();
        $scope.generalinfos = GeneralInfo.query({filter: 'report-is-null'});
        $q.all([$scope.report.$promise, $scope.generalinfos.$promise]).then(function() {
            if (!$scope.report.generalInfo || !$scope.report.generalInfo.id) {
                return $q.reject();
            }
            return GeneralInfo.get({id : $scope.report.generalInfo.id}).$promise;
        }).then(function(generalInfo) {
            $scope.generalinfos.push(generalInfo);
        });
        $scope.projectinfos = ProjectInfo.query({filter: 'report-is-null'});
        $q.all([$scope.report.$promise, $scope.projectinfos.$promise]).then(function() {
            if (!$scope.report.projectInfo || !$scope.report.projectInfo.id) {
                return $q.reject();
            }
            return ProjectInfo.get({id : $scope.report.projectInfo.id}).$promise;
        }).then(function(projectInfo) {
            $scope.projectinfos.push(projectInfo);
        });
        $scope.load = function(id) {
            Raport.get({id : id}, function(result) {
                $scope.raport = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('documentmanagementApp:raportUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.raport.id != null) {
                Raport.update($scope.raport, onSaveSuccess, onSaveError);
            } else {
                Raport.save($scope.raport, onSaveSuccess, onSaveError);
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
        $scope.datePickerForSendAt = {};

        $scope.datePickerForSendAt.status = {
            opened: false
        };

        $scope.datePickerForSendAtOpen = function($event) {
            $scope.datePickerForSendAt.status.opened = true;
        };
        $scope.datePickerForReceivedAt = {};

        $scope.datePickerForReceivedAt.status = {
            opened: false
        };

        $scope.datePickerForReceivedAtOpen = function($event) {
            $scope.datePickerForReceivedAt.status.opened = true;
        };
}]);
