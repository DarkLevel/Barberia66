/* global moduleCommon */

'use strict';

moduleCommon.controller('operacionesController', ['$scope', 'toolService',
    function ($scope, toolService) {
        $scope.isActive = toolService.isActive;
    }
]);