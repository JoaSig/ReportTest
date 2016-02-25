'use strict';

angular.module('documentmanagementApp')
    .controller('DrillingController', function ($scope, $state, Drilling, DrillingSearch, ParseLinks) {

        $scope.drillings = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            DrillingSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.drillings = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.drilling = {
                pileType: null,
                pilingRigType: null,
                pileNr: null,
                pileLength: null,
                diameter: null,
                topGuideElevation: null,
                pileTopLevel: null,
                pileCutoffElevation: null,
                pileToeLevel: null,
                casingDeviation: null,
                startDate: null,
                endDate: null,
                startTime: null,
                endTime: null,
                comment: null,
                id: null
            };
        };
    });
