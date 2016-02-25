'use strict';

angular.module('documentmanagementApp')
    .controller('ConcretingController', function ($scope, $state, Concreting, ConcretingSearch, ParseLinks) {

        $scope.concretings = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Concreting.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.concretings = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            ConcretingSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.concretings = result;
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
            $scope.concreting = {
                createdAt: null,
                lastUpdatedAt: null,
                lastUpdatedBy: null,
                mixDesign: null,
                slumpFlowTest: null,
                pouringRate: null,
                totalCastedVolume: null,
                overConsumption: null,
                comment: null,
                startDate: null,
                endDate: null,
                startTime: null,
                endTime: null,
                calculatedCumulativeCls: null,
                calculatedTheoreticCls: null,
                calculatedDifference: null,
                calculatedPercent: null,
                sentBack: null,
                id: null
            };
        };
    });
