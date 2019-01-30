/* global moduleRegistro */

'use strict';

moduleRegistro.controller('registroCalendarioController', ['$scope', 'toolService',
    function ($scope, toolService) {
        $scope.isActive = toolService.isActive;
    }
]);