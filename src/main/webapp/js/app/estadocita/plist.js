/* global moduleEstadocita */

'use strict';

moduleEstadocita.controller('estadocitaPlistController', ['$scope', 'toolService',
    function ($scope, toolService) {
        $scope.isActive = toolService.isActive;
    }
]);