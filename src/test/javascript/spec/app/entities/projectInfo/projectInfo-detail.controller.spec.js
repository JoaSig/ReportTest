'use strict';

describe('Controller Tests', function() {

    describe('ProjectInfo Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockProjectInfo, MockUser, MockPile;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockProjectInfo = jasmine.createSpy('MockProjectInfo');
            MockUser = jasmine.createSpy('MockUser');
            MockPile = jasmine.createSpy('MockPile');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ProjectInfo': MockProjectInfo,
                'User': MockUser,
                'Pile': MockPile
            };
            createController = function() {
                $injector.get('$controller')("ProjectInfoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'documentmanagementApp:projectInfoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
