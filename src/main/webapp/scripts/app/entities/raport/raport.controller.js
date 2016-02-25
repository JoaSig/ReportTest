'use strict';

angular.module('documentmanagementApp')
    .controller('RaportController', function ($scope, $state, Raport, RaportSearch, ParseLinks) {

        $scope.raports = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Raport.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.raports = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            RaportSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.raports = result;
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
            $scope.raport = {
                createdAt: null,
                lastUpdatedAt: null,
                lastUpdatedBy: null,
                sendAt: null,
                receivedAt: null,
                description: null,
                comment: null,
                emails: null,
                id: null
            };
        };
    });
