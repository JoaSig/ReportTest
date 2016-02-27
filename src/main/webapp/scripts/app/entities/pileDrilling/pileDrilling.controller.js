'use strict';

angular.module('documentmanagementApp')
    .controller('PileDrillingController', function ($scope, $state, PileDrilling, PileDrillingSearch, ParseLinks, $filter, ngTableParams) {

        $scope.pileDrillings = [];
        $scope.machines = [];
        $scope.pileDrillingsByMachine = null;
        $scope.predicate = 'drillingId';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function () {
            console.log('loadAll: ' + $scope.predicate);
            PileDrilling.query({
                page: $scope.page - 1,
                size: 400,
                sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'drillingId']
            }, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.pileDrillings = result;
                $scope.tableParams.reload();
            });
        };
        $scope.loadPage = function (page) {
            $scope.page = page;
            $scope.loadAll();
            $scope.tableParams.reload();
        };
        $scope.loadAll();

        $scope.tableParams = new ngTableParams({
            page: 1,
            count: 40,
            sorting: {
                drillingId: 'asc'     // initial sorting
            }
        }, {
            total: 0,
            getData: function ($defer, params) {
                var orderedData = params.sorting() ? $filter('orderBy')($scope.pileDrillings, params.orderBy()) : $scope.pileDrillings;
                var filteredData = params.filter() ? $filter('filter')(orderedData, params.filter()) : orderedData;
                params.total(filteredData.length);

                self.pageCount = params.count;

                $scope.data = filteredData.slice((params.page() - 1) * params.count(), params.page() * params.count());
                $defer.resolve($scope.data);
            }
        });
        $scope.tableParams.settings().$scope = $scope;

        $scope.search = function () {
            PileDrillingSearch.query({query: $scope.searchQuery}, function (result) {
                $scope.pileDrillings = result;
                $scope.tableParams.reload();
            }, function (response) {
                if (response.status === 404) {
                    $scope.loadAll();
                    $scope.tableParams.reload();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.tableParams.reload();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.pileDrilling = {
                drillingMachine: null,
                projectDepth: null,
                effectiveDepth: null,
                StartDate: null,
                EndDate: null,
                StartTime: null,
                EndTime: null,
                drillingId: null,
                id: null
            };
        };
    });
