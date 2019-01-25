/* global moduleProducto */

'use strict';

moduleProducto.controller('productoPlistController', ['$scope', 'toolService',
    function ($scope, toolService) {
        $scope.isActive = toolService.isActive;
    }
]);