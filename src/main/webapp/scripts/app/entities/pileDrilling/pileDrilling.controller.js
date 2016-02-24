'use strict';

angular.module('documentmanagementApp')
    .controller('PileDrillingController', function ($scope, $state, PileDrilling, PileDrillingSearch, ParseLinks, $filter, ngTableParams, Chart) {

        $scope.pileDrillings = [];
        $scope.machines = [];
        $scope.pileDrillingsByMachine = null;
        $scope.predicate = 'drillingMachine';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function () {
            console.log('loadAll: ' + $scope.predicate);
            PileDrilling.query({
                page: $scope.page - 1,
                size: 20,
                sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']
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
        };
        $scope.loadAll();

        PileDrilling.machines(function (machines) {
            if (!$scope.machines.length) {
                $scope.machines = machines;
            }
        });

        $scope.tableParams = new ngTableParams({
            page: 1,
            count: 20,
            sorting: {
                drillingMachine: 'asc'     // initial sorting
            }
        }, {
            total: 0,
            getData: function ($defer, params) {
                var orderedData = params.sorting() ? $filter('orderBy')($scope.pileDrillings, params.orderBy()) : $scope.pileDrillings;
                var filteredData = params.filter() ? $filter('filter')(orderedData, params.filter()) : orderedData;
                params.total(filteredData.length);
                console.log('$scope.pileDrillings: ' + $scope.pileDrillings.length);

                self.pageCount = params.count;

                $scope.data = filteredData.slice((params.page() - 1) * params.count(), params.page() * params.count());
                $defer.resolve($scope.data);
            }
        });
        $scope.tableParams.settings().$scope = $scope;

        $scope.search = function () {
            PileDrillingSearch.query({query: $scope.searchQuery}, function (result) {
                $scope.pileDrillings = result;
            }, function (response) {
                if (response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.machine = function () {
            PileDrilling.machine({drillingMachine: $scope.drillingMachine}, function (pileDrillingsByMachine) {
                $scope.drilling = pileDrillingsByMachine.machine;
                $scope.pileDrillingsByMachine = pileDrillingsByMachine;
                function getHighestId(drillings) {
                    var highest = 0;
                    drillings.forEach(function (drilling) {
                        if (drilling.id > highest) {
                            highest = drilling.id;
                        }
                    });
                    console.log('highest: ' + highest);
                    return highest;
                }

                function getLowestId(drillings) {
                    var lowest = 500;
                    drillings.forEach(function (drilling) {
                        if (drilling.id < lowest) {
                            lowest = drilling.id;
                        }
                    });
                    console.log('lowest: ' + lowest);
                    return lowest;
                }

                if (pileDrillingsByMachine.drillings.length) {
                    $scope.pdOptions = angular.copy(Chart.getPDChartConfig());
                    $scope.pdOptions.title.text = pileDrillingsByMachine.machine;
                    $scope.pdOptions.chart.yAxis.axisLabel = "Drilling Time (mm)";
                    $scope.pdOptions.chart.xDomain = [getLowestId(pileDrillingsByMachine.drillings), getHighestId(pileDrillingsByMachine.drillings)];
                    var drillingMinutes = [];
                    pileDrillingsByMachine.drillings.forEach(function (drilling) {
                        var totalDrillingMinutes = pileDrillingsByMachine.drillingMinutesMap[drilling.id];
                        drillingMinutes.push({
                            x: drilling.id,
                            y: totalDrillingMinutes
                        });
                    });
                    $scope.pdData = [{
                        values: drillingMinutes,
                        key: 'DrillingMinutes',
                        color: '#673ab7'
                    }];
                }

            }, function (response) {
                if (response.status === 404) {
                    $scope.loadAll();
                }
            })
        };

        $scope.getMachineForGraph = function () {
            console.log('getMachineForGraph for: ' + $scope.drillingMachine);
            $scope.machine($scope.drillingMachine);
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
