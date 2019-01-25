'use strict';

moduleLinea.controller('lineaPlistController', ['$scope', 'toolService',
    function ($scope, toolService) {
        $scope.isActive = toolService.isActive;
    }
]);