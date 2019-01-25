/* global moduleTipoproducto */

'use strict';

moduleTipoproducto.controller('tipoproductoPlistController', ['$scope', 'toolService',
    function ($scope, toolService) {
        $scope.isActive = toolService.isActive;
    }
]);