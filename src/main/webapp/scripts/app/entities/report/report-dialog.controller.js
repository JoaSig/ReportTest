'use strict';

angular.module('documentmanagementApp').controller('ReportDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Report', 'User', 'Signature', 'GeneralInfo', 'ProjectInfo',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Report, User, Signature, GeneralInfo, ProjectInfo) {

        $scope.report = entity;
        $scope.users = User.query();
        $scope.signatures = Signature.query();
        $scope.generalinfos = GeneralInfo.query({filter: 'general_info_id-is-null'});
        $q.all([$scope.general_info_id.$promise, $scope.generalinfos.$promise]).then(function() {
            if (!$scope.general_info_id.generalInfo || !$scope.general_info_id.generalInfo.id) {
                return $q.reject();
            }
            return GeneralInfo.get({id : $scope.general_info_id.generalInfo.id}).$promise;
        }).then(function(generalInfo) {
            $scope.generalinfos.push(generalInfo);
        });
        $scope.projectinfos = ProjectInfo.query({filter: 'project_info_id-is-null'});
        $q.all([$scope.project_info_id.$promise, $scope.projectinfos.$promise]).then(function() {
            if (!$scope.project_info_id.projectInfo || !$scope.project_info_id.projectInfo.id) {
                return $q.reject();
            }
            return ProjectInfo.get({id : $scope.project_info_id.projectInfo.id}).$promise;
        }).then(function(projectInfo) {
            $scope.projectinfos.push(projectInfo);
        });
        $scope.load = function(id) {
            Report.get({id : id}, function(result) {
                $scope.report = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('documentmanagementApp:reportUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.report.id != null) {
                Report.update($scope.report, onSaveSuccess, onSaveError);
            } else {
                Report.save($scope.report, onSaveSuccess, onSaveError);
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
