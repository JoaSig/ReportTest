'use strict';

angular.module('documentmanagementApp')
    .controller('PileController', function ($scope, $state, Pile, PileSearch, ParseLinks) {

        $scope.piles = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            PileSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.piles = result;
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
            $scope.pile = {
                createdAt: null,
                lastUpdatedAt: null,
                lastUpdatedBy: null,
                nextPile: null,
                prevPile: null,
                number: null,
                comment: null,
                id: null
            };
        };
    });
