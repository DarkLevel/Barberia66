/* global moduleRegistro */

'use strict';

moduleRegistro.controller('registroCalendarioController', ['$scope', 'toolService', '$anchorScroll',
    function ($scope, toolService, $anchorScroll) {
        $anchorScroll();
        
        $scope.isActive = toolService.isActive;
    }
]);