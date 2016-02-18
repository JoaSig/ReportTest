'use strict';

describe('Controller Tests', function() {

    describe('PileConcreting Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPileConcreting, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPileConcreting = jasmine.createSpy('MockPileConcreting');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'PileConcreting': MockPileConcreting,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("PileConcretingDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'documentmanagementApp:pileConcretingUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
