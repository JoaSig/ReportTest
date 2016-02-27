'use strict';

angular.module('documentmanagementApp')
    .controller('DashboardController', function ($scope, Principal, PileDrilling, Chart) {
        var self = this;

        $scope.barChars = [];
        $scope.machines = [];
        self.totalDepth = 0;
        self.totalMin = 0;
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

        //$scope.machine = function () {
        //    PileDrilling.machine({drillingMachine: $scope.drillingMachine}, function (pileDrillingsByMachine) {
        //        $scope.drilling = pileDrillingsByMachine.machine;
        //        $scope.pileDrillingsByMachine = pileDrillingsByMachine;
        //        $scope.pileDrillingMachines.push(pileDrillingsByMachine);
        //        self.totalDepth = pileDrillingsByMachine.totalDrillingDepth;
        //        self.totalMin = pileDrillingsByMachine.totalDrillTime;
        //        var expected = [];
        //        var actual = [];
        //        var labels = [];
        //        if (pileDrillingsByMachine.drillings.length) {
        //            pileDrillingsByMachine.drillings.forEach(function (drilling) {
        //                expected.push(drilling.projectDepth);
        //                actual.push(drilling.effectiveDepth);
        //                labels.push(drilling.startDate);
        //            });
        //        }
        //        $scope.labels = labels;
        //        $scope.series = ['Project Drilling Depth', 'Effective Drilling Depth'];
        //
        //        $scope.data = [expected, actual];
        //        var steps = 0.5;
        //        $scope.barOptions = {
        //            animation: false,
        //            scaleBeginAtZero: false,
        //            scaleOverride: true,
        //            scaleSteps: steps,
        //            scaleFontSize: 14,
        //            scaleStartValue: 27,
        //            scaleStepWidth: Math.ceil(2 / steps)
        //        };
        //
        //        // doughnut
        //        $scope.doughnutLabels = ["Meter per Hour", "Peak performance"];
        //        $scope.doughnutData = [pileDrillingsByMachine.meterDrillingPerHour, 10];
        //    }, function (response) {
        //        if (response.status === 404) {
        //            $scope.loadAll();
        //        }
        //    })
        //};

        $scope.getMachineForGraph = function () {
            //$scope.machine($scope.drillingMachine);
            $scope.machinePerMinute($scope.drillMachine);
            $scope.machinePerDepth();
        };


        $scope.machinePerMinute = function () {
            PileDrilling.machine({drillingMachine: $scope.drillMachine}, function (drillingsByMachine) {
                $scope.drilling = drillingsByMachine.machine;
                $scope.drillingsByMachine = drillingsByMachine;
                self.totalDepth = drillingsByMachine.totalDrillingDepth;
                self.totalMin = drillingsByMachine.totalDrillingMinutes;
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
                    $scope.pdOptions.chart.yAxis.axisLabel = "Drilling Time (min)";
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
                        height: 250,
                        donut: true,
                        x: function (d) {
                            return d.key;
                        },
                        y: function (d) {
                            return d.y;
                        },
                        showLabels: true,

                        pie: {
                            startAngle: function (d) {
                                return d.startAngle / 2 - Math.PI / 2
                            },
                            endAngle: function (d) {
                                return d.endAngle / 2 - Math.PI / 2
                            }
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
                        key: "Meter per hour: " + drillingsByMachine.meterDrillingPerHour,
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

        $scope.machinePerDepth = function () {
            PileDrilling.machine({drillingMachine: $scope.drillDepthMachine}, function (drillingsByMachine) {
                $scope.drilling = drillingsByMachine.machine;
                $scope.drillingDepthMachine = drillingsByMachine;
                self.totalDepthMachine = drillingsByMachine.totalDrillingDepth;
                self.totalDepthMinMachine = drillingsByMachine.totalDrillingMinutes;
                function getHighestDepth(drillings) {
                    var highest = 0;
                    drillings.forEach(function (drilling) {
                        var depth = 0;
                        if (drilling.effectiveDepth > drilling.projectDepth) {
                            depth = drilling.effectiveDepth;
                        } else {
                            depth = drilling.projectDepth;
                        }
                        if (depth > highest) {
                            highest = depth;
                        }
                    });
                    console.log('highest: ' + highest);
                    return highest;
                }

                function getLowestDepth(drillings) {
                    var lowest = 500;
                    drillings.forEach(function (drilling) {
                        var depth = 0;
                        if (drilling.effectiveDepth < drilling.projectDepth) {
                            depth = drilling.effectiveDepth;
                        } else {
                            depth = drilling.projectDepth;
                        }
                        if (depth < lowest) {
                            lowest = depth;
                        }
                    });
                    console.log('lowest: ' + lowest);
                    return lowest;
                }

                if (drillingsByMachine.drillings.length) {
                    $scope.multiBarOptions = angular.copy(Chart.getMultiBarChartConfig());
                    //console.log('drillingsByMachine.drillings.length_ ' + drillingsByMachine.drillings.length);
                    //if (drillingsByMachine.drillings.length < 100) {
                    //    $scope.multiBarOptions.chart.width = 100;
                    //}
                    //if (drillingsByMachine.drillings.length < 200) {
                    //    $scope.multiBarOptions.chart.width = 2000;
                    //}
                    //if (drillingsByMachine.drillings.length < 300) {
                    //    $scope.multiBarOptions.chart.width = 3000;
                    //}

                    $scope.multiBarOptions.title.text = drillingsByMachine.machine;
                    $scope.multiBarOptions.chart.xAxis.axisLabel = "Start Date";
                    $scope.multiBarOptions.chart.yAxis.axisLabel = "Depth (m)";
                    $scope.multiBarOptions.chart.yDomain = [getLowestDepth(drillingsByMachine.drillings), getHighestDepth(drillingsByMachine.drillings)];
                    //$scope.multiBarOptions.chart.xDomain = [getLowestId(drillingsByMachine.drillings), getHighestId(drillingsByMachine.drillings)];
                    var plannedDepth = [];
                    var actualDepth = [];
                    drillingsByMachine.drillings.forEach(function (drilling) {
                        plannedDepth.push({
                            x: drilling.startDate,
                            y: drilling.projectDepth
                        });
                        actualDepth.push({
                            x: drilling.startDate,
                            y: drilling.effectiveDepth
                        });
                    });
                    $scope.multiBarData = [{
                        values: plannedDepth,
                        key: 'Project Depth',
                        color: '#686cf5'
                    }, {
                        values: actualDepth,
                        key: 'Effective Depth',
                        color: '#c8d9f5'
                    }];
                }


                $scope.pieDepthOptions = {
                    chart: {
                        type: 'pieChart',
                        height: 250,
                        donut: true,
                        x: function (d) {
                            return d.key;
                        },
                        y: function (d) {
                            return d.y;
                        },
                        showLabels: true,

                        pie: {
                            startAngle: function (d) {
                                return d.startAngle / 2 - Math.PI / 2
                            },
                            endAngle: function (d) {
                                return d.endAngle / 2 - Math.PI / 2
                            }
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

                $scope.pieDepthData = [
                    {
                        key: "Meter per hour: " + drillingsByMachine.meterDrillingPerHour,
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
