'use strict';

angular.module('documentmanagementApp')
    .controller('PileConcretingController', function ($scope, $state, PileConcreting, PileConcretingSearch, ParseLinks) {

        $scope.pileConcretings = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            PileConcreting.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.pileConcretings = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            PileConcretingSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pileConcretings = result;
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
            $scope.pileConcreting = {
                mixDesign: null,
                slump1: null,
                slump2: null,
                slump3: null,
                slump4: null,
                slump5: null,
                truckId1: null,
                truckId2: null,
                truckId3: null,
                truckId4: null,
                truckId5: null,
                casted1: null,
                casted2: null,
                casted3: null,
                casted4: null,
                casted5: null,
                concretingDateStart: null,
                concretingStartTime: null,
                concretingEndTime: null,
                concretingOrderTime1: null,
                concretingArrivalTime1: null,
                concretingOrderTime2: null,
                concretingArrivalTime2: null,
                concretingOrderTime3: null,
                concretingArrivalTime3: null,
                concretingOrderTime4: null,
                concretingArrivalTime4: null,
                concretingOrderTime5: null,
                concretingArrivalTime5: null,
                calculatedCumulativeCls: null,
                calculatedTheoricCls: null,
                calculatedDifference: null,
                calculatedProcent: null,
                concreteSentBack: null,
                id: null
            };
        };
    });
