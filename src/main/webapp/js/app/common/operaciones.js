/* global moduleCommon */

'use strict';

moduleCommon.controller('operacionesController', ['$scope', 'toolService', 'sessionService',
    function ($scope, toolService, oSessionService) {
        $scope.isAdmin = oSessionService.isAdmin();
        $scope.isBoss = oSessionService.isBoss();
        
        $scope.isActive = toolService.isActive;
    }
]);