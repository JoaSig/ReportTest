'use strict';

angular.module('documentmanagementApp')
    .controller('PileController', function ($scope, $state, Pile, PileSearch, ParseLinks) {

        $scope.piles = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Pile.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.piles = result;
            });
        };
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
                mixDesign: null,
                slumpFlowTest: null,
                pouringRate: null,
                totalCastedVolume: null,
                theoreticalConcreteVolume: null,
                overconsumptionOfConcrete: null,
                comment: null,
                signatureDate: null,
                subContractor: null,
                mainContractor: null,
                client: null,
                id: null
            };
        };
    });
