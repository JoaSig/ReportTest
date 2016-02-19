'use strict';

describe('Controller Tests', function() {

    describe('PileDrilling Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPileDrilling, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPileDrilling = jasmine.createSpy('MockPileDrilling');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'PileDrilling': MockPileDrilling,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("PileDrillingDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'documentmanagementApp:pileDrillingUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
