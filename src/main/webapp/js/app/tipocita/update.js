/* global moduleTipocita */

'use strict';

moduleTipocita.controller('tipocitaUpdateController', ['$scope', '$http', 'toolService', '$routeParams', '$anchorScroll', '$location',
    function ($scope, $http, toolService, $routeParams, $anchorScroll, $location) {
        $anchorScroll();
        
        $scope.formulario = true;
        $scope.botones = true;
        $scope.correcto = false;

        $http({
            method: 'GET',
            url: 'http://localhost:8081/barberia66/barberia66?ob=tipocita&op=get&id=' + $routeParams.id
        }).then(function (response) {
            $scope.status = response.status;
            $scope.id = response.data.message.id;
            $scope.descripcion = response.data.message.descripcion;
            $scope.precio = reemplazar(response.data.message.precio);
        }, function (response) {
            $scope.status = response.status;
            $scope.ajaxData = response.data.message || 'Request failed';
        });

        $scope.volver = function () {
            $location.url('tipocita/plist');
        };

        $scope.editar = function () {
            var json = {
                id: $scope.id,
                descripcion: $scope.descripcion,
                precio: $scope.precio.replace(',', '.')
            };
            $http({
                method: 'GET',
                url: 'http://localhost:8081/barberia66/barberia66?ob=tipocita&op=update',
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
        
        function reemplazar(precio) {
            var precioString = precio.toString();
            var precioCambiado = precioString.replace(".", ",");

            return precioCambiado;
        }

        $scope.isActive = toolService.isActive;
    }
]);