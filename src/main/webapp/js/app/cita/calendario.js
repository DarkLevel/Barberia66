/* global moduleCita */

'use strict';

moduleCita.controller('citaCalendarioController', ['$scope', 'toolService', '$anchorScroll',
    function ($scope, toolService, $anchorScroll) {
        $anchorScroll();
        
        $scope.isActive = toolService.isActive;
    }
]);