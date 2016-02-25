'use strict';

describe('Controller Tests', function() {

    describe('SteelCage Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSteelCage, MockUser, MockPile;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSteelCage = jasmine.createSpy('MockSteelCage');
            MockUser = jasmine.createSpy('MockUser');
            MockPile = jasmine.createSpy('MockPile');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'SteelCage': MockSteelCage,
                'User': MockUser,
                'Pile': MockPile
            };
            createController = function() {
                $injector.get('$controller')("SteelCageDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'documentmanagementApp:steelCageUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
