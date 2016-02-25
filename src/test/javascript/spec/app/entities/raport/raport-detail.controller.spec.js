'use strict';

describe('Controller Tests', function() {

    describe('Raport Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRaport, MockUser, MockSignature, MockGeneralInfo, MockProjectInfo;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRaport = jasmine.createSpy('MockRaport');
            MockUser = jasmine.createSpy('MockUser');
            MockSignature = jasmine.createSpy('MockSignature');
            MockGeneralInfo = jasmine.createSpy('MockGeneralInfo');
            MockProjectInfo = jasmine.createSpy('MockProjectInfo');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Raport': MockRaport,
                'User': MockUser,
                'Signature': MockSignature,
                'GeneralInfo': MockGeneralInfo,
                'ProjectInfo': MockProjectInfo
            };
            createController = function() {
                $injector.get('$controller')("RaportDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'documentmanagementApp:raportUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
