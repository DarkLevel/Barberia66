/* global moduleUsuario */

'use strict';

moduleUsuario.controller('usuarioCreateController', ['$scope', '$http', 'toolService', '$anchorScroll',
    function ($scope, $http, toolService, $anchorScroll) {
        $anchorScroll();

        $scope.formulario = true;
        $scope.botones = true;
        $scope.correcto = false;
        $scope.fecha_alta = new Date();
        $scope.actualDate = new Date($scope.fecha_alta.getFullYear(), $scope.fecha_alta.getMonth(), $scope.fecha_alta.getDate());

        $http({
            method: 'GET',
            url: 'http://localhost:8081/barberia66/barberia66?ob=tipousuario&op=getpage&rpp=10000&page=1&order=descripcion,asc'
        }).then(function (response) {
            $scope.ajaxDataTipoUsuario = response.data.message;
            var listatipousuarios = [];
            $scope.ajaxDataTipoUsuario.forEach(element => {
                var tipousuarios = {
                    obj_tipousuario: element
                };
                listatipousuarios.push(tipousuarios);
            });
            $scope.tipousuario = listatipousuarios[0].obj_tipousuario.id;
        }, function (response) {
            $scope.status = response.status;
            $scope.ajaxDataTipoUsuario = response.data.message || 'Request failed';
        });

        $scope.volver = function () {
            window.history.back();
        };

        $scope.crear = function () {
            var json = {
                dni: $scope.dni,
                nombre: $scope.nombre,
                apellido1: $scope.apellido1,
                apellido2: $scope.apellido2,
                username: $scope.username,
                password: forge_sha256($scope.password),
                fecha_alta: $scope.fecha_alta,
                id_tipousuario: $scope.tipousuario
            };
            $http({
                method: 'GET',
                url: 'http://localhost:8081/barberia66/barberia66?ob=usuario&op=create',
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