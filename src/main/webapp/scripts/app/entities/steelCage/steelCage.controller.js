'use strict';

angular.module('documentmanagementApp')
    .controller('SteelCageController', function ($scope, $state, SteelCage, SteelCageSearch, ParseLinks) {

        $scope.steelCages = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            SteelCage.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.steelCages = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            SteelCageSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.steelCages = result;
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
            $scope.steelCage = {
                createdAt: null,
                idCage: null,
                sonicPipesCompliance: null,
                waterAndCappingSonicPipeFilling: null,
                overlappingCompliance: null,
                spacerPositionCompliance: null,
                distanceBetweenCageTopAndGuideWallEdge: null,
                verticalityCompliance: null,
                lastUpdatedAt: null,
                lastUpdatedBy: null,
                startTime: null,
                endTime: null,
                comment: null,
                id: null
            };
        };
    });
