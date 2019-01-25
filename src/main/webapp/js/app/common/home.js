/* global moduleCommon */

'use strict';

moduleCommon.controller('homeController', ['$scope', 'toolService',
    function ($scope, toolService) {
        $scope.isActive = toolService.isActive;
    }
]);