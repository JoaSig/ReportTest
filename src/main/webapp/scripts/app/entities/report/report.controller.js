'use strict';

angular.module('documentmanagementApp')
    .controller('ReportController', function ($scope, $state, Report, ReportSearch, ParseLinks) {

        $scope.reports = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Report.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.reports = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            ReportSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.reports = result;
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
            $scope.report = {
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
