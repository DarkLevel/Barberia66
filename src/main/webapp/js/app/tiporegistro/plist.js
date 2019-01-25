/* global moduleTiporegistro */

'use strict';

moduleTiporegistro.controller('tiporegistroPlistController', ['$scope', 'toolService',
    function ($scope, toolService) {
        $scope.isActive = toolService.isActive;
    }
]);