'use strict';

angular.module('documentmanagementApp')
    .controller('ProjectInfoController', function ($scope, $state, ProjectInfo, ProjectInfoSearch, ParseLinks) {

        $scope.projectInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            ProjectInfo.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.projectInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            ProjectInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.projectInfos = result;
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
            $scope.projectInfo = {
                name: null,
                wirCode: null,
                phase: null,
                site: null,
                subContractor: null,
                recordSN: null,
                contractNr: null,
                id: null
            };
        };
    });
