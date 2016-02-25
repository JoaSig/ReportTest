'use strict';

angular.module('documentmanagementApp')
    .controller('GeneralInfoController', function ($scope, $state, GeneralInfo, GeneralInfoSearch, ParseLinks) {

        $scope.generalInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            GeneralInfo.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.generalInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            GeneralInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.generalInfos = result;
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
            $scope.generalInfo = {
                treviRefNr: null,
                pilingRigType: null,
                machineSerialNr: null,
                date: null,
                id: null
            };
        };
    });
