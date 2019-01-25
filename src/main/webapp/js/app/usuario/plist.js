/* global moduleUsuario */

'use strict';

moduleUsuario.controller('usuarioPlistController', ['$scope', 'toolService',
    function ($scope, toolService) {
        $scope.isActive = toolService.isActive;
    }
]);