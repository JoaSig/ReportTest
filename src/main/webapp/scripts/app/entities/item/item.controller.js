'use strict';

angular.module('documentmanagementApp')
    .controller('ItemController', function ($scope, $state, Item, ItemSearch, ParseLinks) {

        $scope.items = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            ItemSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.items = result;
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
            $scope.item = {
                orderTime: null,
                arrivalTime: null,
                truck: null,
                casted: null,
                slump: null,
                theoreticalConcreteVolume: null,
                id: null
            };
        };
    });
