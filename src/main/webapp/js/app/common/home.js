/* global moduleCommon */

'use strict';

moduleCommon.controller('homeController', ['$scope', 'toolService', '$anchorScroll',
    function ($scope, toolService, $anchorScroll) {
        $anchorScroll();
        
        $scope.isActive = toolService.isActive;
    }
]);