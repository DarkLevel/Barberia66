/* global moduleCita */

'use strict';

moduleCita.controller('citaPlistController', ['$scope', 'toolService',
    function ($scope, toolService) {
        $scope.isActive = toolService.isActive;
    }
]);