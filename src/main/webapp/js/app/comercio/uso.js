/* global moduleComercio */

'use strict';

moduleComercio.controller('comercioUsoController', ['$scope', 'toolService',
    function ($scope, toolService) {
        $scope.isActive = toolService.isActive;
    }
]);