/* global moduleTipocita */

'use strict';

moduleTipocita.controller('tipocitaPlistController', ['$scope', 'toolService',
    function ($scope, toolService) {
        $scope.isActive = toolService.isActive;
    }
]);