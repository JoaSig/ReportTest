'use strict';

angular.module('documentmanagementApp')
    .controller('ImmutableReportController', function ($scope, $state, ImmutableReport, ImmutableReportSearch, ParseLinks) {

        $scope.immutableReports = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            ImmutableReport.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.immutableReports = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            ImmutableReportSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.immutableReports = result;
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
            $scope.immutableReport = {
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
