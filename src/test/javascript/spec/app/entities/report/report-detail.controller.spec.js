'use strict';

describe('Controller Tests', function() {

    describe('Report Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockReport, MockUser, MockSignature, MockGeneralInfo, MockProjectInfo;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockReport = jasmine.createSpy('MockReport');
            MockUser = jasmine.createSpy('MockUser');
            MockSignature = jasmine.createSpy('MockSignature');
            MockGeneralInfo = jasmine.createSpy('MockGeneralInfo');
            MockProjectInfo = jasmine.createSpy('MockProjectInfo');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Report': MockReport,
                'User': MockUser,
                'Signature': MockSignature,
                'GeneralInfo': MockGeneralInfo,
                'ProjectInfo': MockProjectInfo
            };
            createController = function() {
                $injector.get('$controller')("ReportDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'documentmanagementApp:reportUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
