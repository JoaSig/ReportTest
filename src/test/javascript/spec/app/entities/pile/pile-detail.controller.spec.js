'use strict';

describe('Controller Tests', function() {

    describe('Pile Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPile, MockUser, MockConcreting, MockDrilling, MockSteelCage;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPile = jasmine.createSpy('MockPile');
            MockUser = jasmine.createSpy('MockUser');
            MockConcreting = jasmine.createSpy('MockConcreting');
            MockDrilling = jasmine.createSpy('MockDrilling');
            MockSteelCage = jasmine.createSpy('MockSteelCage');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Pile': MockPile,
                'User': MockUser,
                'Concreting': MockConcreting,
                'Drilling': MockDrilling,
                'SteelCage': MockSteelCage
            };
            createController = function() {
                $injector.get('$controller')("PileDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'documentmanagementApp:pileUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
