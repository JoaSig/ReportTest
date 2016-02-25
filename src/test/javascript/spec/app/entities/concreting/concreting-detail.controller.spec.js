'use strict';

describe('Controller Tests', function() {

    describe('Concreting Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockConcreting, MockUser, MockPile, MockItem;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockConcreting = jasmine.createSpy('MockConcreting');
            MockUser = jasmine.createSpy('MockUser');
            MockPile = jasmine.createSpy('MockPile');
            MockItem = jasmine.createSpy('MockItem');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Concreting': MockConcreting,
                'User': MockUser,
                'Pile': MockPile,
                'Item': MockItem
            };
            createController = function() {
                $injector.get('$controller')("ConcretingDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'documentmanagementApp:concretingUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
