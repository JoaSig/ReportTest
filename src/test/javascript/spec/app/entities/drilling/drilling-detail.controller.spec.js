'use strict';

describe('Controller Tests', function() {

    describe('Drilling Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDrilling, MockUser, MockPile;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDrilling = jasmine.createSpy('MockDrilling');
            MockUser = jasmine.createSpy('MockUser');
            MockPile = jasmine.createSpy('MockPile');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Drilling': MockDrilling,
                'User': MockUser,
                'Pile': MockPile
            };
            createController = function() {
                $injector.get('$controller')("DrillingDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'documentmanagementApp:drillingUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
