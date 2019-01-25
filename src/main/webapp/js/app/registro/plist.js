/* global moduleRegistro */

'use strict';

moduleRegistro.controller('registroPlistController', ['$scope', 'toolService',
    function ($scope, toolService) {
        $scope.isActive = toolService.isActive;
    }
]);