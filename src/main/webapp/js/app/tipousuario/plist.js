/* global moduleTipousuario */

'use strict';

moduleTipousuario.controller('tipousuarioPlistController', ['$scope', 'toolService',
    function ($scope, toolService) {
        $scope.isActive = toolService.isActive;
    }
]);