'use strict';

angular.module('documentmanagementApp').controller('PileDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Pile', 'User', 'Concreting', 'Drilling', 'SteelCage',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Pile, User, Concreting, Drilling, SteelCage) {

        $scope.pile = entity;
        $scope.users = User.query();
        $scope.concretings = Concreting.query({filter: 'pile-is-null'});
        $q.all([$scope.pile.$promise, $scope.concretings.$promise]).then(function() {
            if (!$scope.pile.concreting || !$scope.pile.concreting.id) {
                return $q.reject();
            }
            return Concreting.get({id : $scope.pile.concreting.id}).$promise;
        }).then(function(concreting) {
            $scope.concretings.push(concreting);
        });
        $scope.drillings = Drilling.query({filter: 'pile-is-null'});
        $q.all([$scope.pile.$promise, $scope.drillings.$promise]).then(function() {
            if (!$scope.pile.drilling || !$scope.pile.drilling.id) {
                return $q.reject();
            }
            return Drilling.get({id : $scope.pile.drilling.id}).$promise;
        }).then(function(drilling) {
            $scope.drillings.push(drilling);
        });
        $scope.steelcages = SteelCage.query({filter: 'pile-is-null'});
        $q.all([$scope.pile.$promise, $scope.steelcages.$promise]).then(function() {
            if (!$scope.pile.steelCage || !$scope.pile.steelCage.id) {
                return $q.reject();
            }
            return SteelCage.get({id : $scope.pile.steelCage.id}).$promise;
        }).then(function(steelCage) {
            $scope.steelcages.push(steelCage);
        });
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
}]);
