'use strict';

describe('Controller Tests', function() {

    describe('Signature Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSignature, MockUser, MockReport;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSignature = jasmine.createSpy('MockSignature');
            MockUser = jasmine.createSpy('MockUser');
            MockReport = jasmine.createSpy('MockReport');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Signature': MockSignature,
                'User': MockUser,
                'Report': MockReport
            };
            createController = function() {
                $injector.get('$controller')("SignatureDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'documentmanagementApp:signatureUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
