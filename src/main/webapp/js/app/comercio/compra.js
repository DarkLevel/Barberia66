/* global moduleComercio */

'use strict';

moduleComercio.controller('comercioCompraController', ['$scope', 'toolService',
    function ($scope, toolService) {
        $scope.isActive = toolService.isActive;
    }
]);