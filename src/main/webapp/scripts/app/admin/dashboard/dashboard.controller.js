'use strict';

angular.module('documentmanagementApp')
    .controller('DashboardController', function ($scope, Principal, PileDrilling, Chart) {
        var self = this;

        $scope.barChars = [];
        $scope.machines = [];
        $scope.authorities = ["ROLE_ADMIN"];
        $scope.pileDrillingMachines = [{}];

        Principal.identity().then(function (account) {
            $scope.currentAccount = account;
        });
        $scope.loadPage = function (page) {
            $scope.page = page;
        };
        PileDrilling.machines(function (machines) {
            if (!$scope.machines.length) {
                $scope.machines = machines;
            }
        });

        $scope.machine = function () {
            PileDrilling.machine({drillingMachine: $scope.drillingMachine}, function (pileDrillingsByMachine) {
                $scope.drilling = pileDrillingsByMachine.machine;
                $scope.pileDrillingsByMachine = pileDrillingsByMachine;
                $scope.pileDrillingMachines.push(pileDrillingsByMachine);
                var expected = [];
                var actual = [];
                var labels = [];
                if (pileDrillingsByMachine.drillings.length) {
                    pileDrillingsByMachine.drillings.forEach(function (drilling) {
                        expected.push(drilling.projectDrillingDepth);
                        actual.push(drilling.drillingEffectiveDepth);
                        labels.push(drilling.drillingStartDate);
                    });
                }
                $scope.labels = labels;
                $scope.series = ['Expected Drilling Depth', 'Actual Drilling Depth'];

                $scope.data = [expected,actual];
                $scope.barOptions = {
                    animation: false,
                    scaleBeginAtZero : false
                };

                // doughnut
                $scope.doughnutLabels = ["Meter per Hour", "Peak performance"];
                $scope.doughnutData = [pileDrillingsByMachine.meterDrillingPerHour, 10];
            }, function (response) {
                if (response.status === 404) {
                    $scope.loadAll();
                }
            })
        };

        $scope.getMachineForGraph = function () {
            $scope.machine($scope.drillingMachine);
            $scope.machinePerMinute($scope.drillMachine);
        };


        $scope.machinePerMinute = function () {
            PileDrilling.machine({drillingMachine: $scope.drillMachine}, function (drillingsByMachine) {
                $scope.drilling = drillingsByMachine.machine;
                $scope.drillingsByMachine = drillingsByMachine;
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

                if (drillingsByMachine.drillings.length) {
                    $scope.pdOptions = angular.copy(Chart.getPDChartConfig());
                    $scope.pdOptions.title.text = drillingsByMachine.machine;
                    $scope.pdOptions.chart.yAxis.axisLabel = "Drilling Time (mm)";
                    $scope.pdOptions.chart.xDomain = [getLowestId(drillingsByMachine.drillings), getHighestId(drillingsByMachine.drillings)];
                    var drillingMinutes = [];
                    drillingsByMachine.drillings.forEach(function (drilling) {
                        var totalDrillingMinutes = drillingsByMachine.drillingMinutesMap[drilling.id];
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


                $scope.pieOptions = {
                    chart: {
                        type: 'pieChart',
                        height: 450,
                        donut: true,
                        x: function(d){return d.key;},
                        y: function(d){return d.y;},
                        showLabels: true,

                        pie: {
                            startAngle: function(d) { return d.startAngle/2 -Math.PI/2 },
                            endAngle: function(d) { return d.endAngle/2 -Math.PI/2 }
                        },
                        duration: 500,
                        legend: {
                            margin: {
                                top: 5,
                                right: 70,
                                bottom: 5,
                                left: 0
                            }
                        }
                    }
                };

                $scope.pieData = [
                    {
                        key: "Meter per hour",
                        y: drillingsByMachine.meterDrillingPerHour
                    },
                    {
                        key: "",
                        y: 10 - drillingsByMachine.meterDrillingPerHour
                    }
                ];



            }, function (response) {
                if (response.status === 404) {
                    $scope.loadAll();
                }
            })
        };
    });
