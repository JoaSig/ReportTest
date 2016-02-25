'use strict';

describe('Controller Tests', function() {

    describe('ImmutableReport Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockImmutableReport, MockUser, MockSignature, MockGeneralInfo, MockProjectInfo;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockImmutableReport = jasmine.createSpy('MockImmutableReport');
            MockUser = jasmine.createSpy('MockUser');
            MockSignature = jasmine.createSpy('MockSignature');
            MockGeneralInfo = jasmine.createSpy('MockGeneralInfo');
            MockProjectInfo = jasmine.createSpy('MockProjectInfo');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ImmutableReport': MockImmutableReport,
                'User': MockUser,
                'Signature': MockSignature,
                'GeneralInfo': MockGeneralInfo,
                'ProjectInfo': MockProjectInfo
            };
            createController = function() {
                $injector.get('$controller')("ImmutableReportDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'documentmanagementApp:immutableReportUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
