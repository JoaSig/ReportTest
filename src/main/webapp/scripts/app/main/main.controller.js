'use strict';

angular.module('documentmanagementApp')
    .controller('MainController', function ($scope, Principal, PileDrilling) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
        PileDrilling.machine = function (pileDrillings) {
            console.log('MACHINE !!! : ' + JSON.stringify(pileDrillings));
            $scope.drilling = pileDrillings.drillingMachine;
            $scope.drillingsPercentage = (pileDrillings / 21) * 100;
        }
    });
