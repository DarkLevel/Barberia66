/* global moduleCita */

'use strict';

moduleCita.controller('citaCalendarioController', ['$scope', 'toolService',
    function ($scope, toolService) {
        $scope.isActive = toolService.isActive;
    }
]);