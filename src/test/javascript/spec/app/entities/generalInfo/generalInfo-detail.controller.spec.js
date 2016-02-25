'use strict';

describe('Controller Tests', function() {

    describe('GeneralInfo Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockGeneralInfo;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockGeneralInfo = jasmine.createSpy('MockGeneralInfo');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'GeneralInfo': MockGeneralInfo
            };
            createController = function() {
                $injector.get('$controller')("GeneralInfoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'documentmanagementApp:generalInfoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
