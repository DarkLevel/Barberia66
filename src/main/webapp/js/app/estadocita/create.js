/* global moduleEstadocita */

'use strict';

moduleEstadocita.controller('estadocitaCreateController', ['$scope', '$http', 'toolService', '$anchorScroll', '$location',
    function ($scope, $http, toolService, $anchorScroll, $location) {
        $anchorScroll();

        $scope.formulario = true;
        $scope.botones = true;
        $scope.correcto = false;

        $scope.volver = function () {
            $location.url('estadocita/plist');
        };

        $scope.crear = function () {
            var json = {
                descripcion: $scope.descripcion
            };
            $http({
                method: 'GET',
                url: 'http://localhost:8081/barberia66/barberia66?ob=estadocita&op=create',
                params: {json: JSON.stringify(json)}
            }).then(function (response) {
                $scope.status = response.status;
                $scope.ajaxData = response.data.message;
                if ($scope.status === 200) {
                    $scope.formulario = false;
                    $scope.botones = false;
                    $scope.correcto = true;
                }
            }, function (response) {
                $scope.status = response.status;
                $scope.ajaxData = response.data.message || 'Request failed';
            });
        };

        $scope.isActive = toolService.isActive;
    }
]);