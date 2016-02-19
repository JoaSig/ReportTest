'use strict';

angular.module('documentmanagementApp')
    .controller('PileDrillingController', function ($scope, $state, PileDrilling, PileDrillingSearch, ParseLinks) {

        $scope.pileDrillings = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            PileDrilling.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.pileDrillings = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            PileDrillingSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pileDrillings = result;
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
            $scope.pileDrilling = {
                drillingMachine: null,
                projectDrillingDepth: null,
                drillingEffectiveDepth: null,
                drillingStartDate: null,
                drillingEndDate: null,
                drillingStartTime: null,
                drillingEndTime: null,
                id: null
            };
        };
    });
