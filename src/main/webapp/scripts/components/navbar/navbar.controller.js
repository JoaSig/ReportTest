'use strict';

angular.module('documentmanagementApp')
    .controller('NavbarController', function ($scope, $location, $state, Auth, Principal, ENV) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        Principal.identity().then(function (account) {
            $scope.account = account;
        });
        $scope.$state = $state;
        $scope.inProduction = ENV === 'prod';

        $scope.logout = function () {
            Auth.logout();
            $scope.getLoggedInUser();
            $state.go('home');
        };

        $scope.$on('userLogIn', function(event, show) {
            $scope.getLoggedInUser();
        });

        $scope.getLoggedInUser = function () {
            Principal.identity().then(function (account) {
                $scope.account = account;
            });
        }
    });
