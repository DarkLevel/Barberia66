/* global moduleUsuario */

'use strict';

moduleUsuario.controller('usuarioUpdateController', ['$scope', '$http', 'toolService', '$routeParams', '$anchorScroll', '$location',
    function ($scope, $http, toolService, $routeParams, $anchorScroll, $location) {
        $anchorScroll();

        $scope.formulario = true;
        $scope.botones = true;
        $scope.correcto = false;
        $scope.fecha_alta = new Date();
        $scope.actualDate = new Date($scope.fecha_alta.getFullYear(), $scope.fecha_alta.getMonth(), $scope.fecha_alta.getDate());

        $http({
            method: 'GET',
            url: 'http://localhost:8081/barberia66/barberia66?ob=usuario&op=get&id=' + $routeParams.id
        }).then(function (response) {
            $scope.status = response.status;
            $scope.id = response.data.message.id;
            $scope.dni = response.data.message.dni;
            $scope.nombre = response.data.message.nombre;
            $scope.apellido1 = response.data.message.apellido1;
            $scope.apellido2 = response.data.message.apellido2;
            $scope.username = response.data.message.username;
            $scope.color_cita = response.data.message.color_cita;
            $scope.color_cita_realizada = response.data.message.color_cita_realizada;
            $scope.fecha_alta = response.data.message.fecha_alta;
            $scope.obj_tipousuario = response.data.message.obj_tipousuario;
        }, function (response) {
            $scope.status = response.status;
            $scope.ajaxData = response.data.message || 'Request failed';
        }).then(function () {
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
                $scope.tipousuario = $scope.obj_tipousuario.id;
            }, function (response) {
                $scope.status = response.status;
                $scope.ajaxDataTipoUsuario = response.data.message || 'Request failed';
            });
        });

        $scope.volver = function () {
            $location.url('usuario/plist');
        };

        $scope.editar = function () {
            var json = {
                id: $scope.id,
                dni: $scope.dni,
                nombre: $scope.nombre,
                apellido1: $scope.apellido1,
                apellido2: $scope.apellido2,
                username: $scope.username,
                color_cita: $scope.color_cita,
                color_cita_realizada: $scope.color_cita_realizada,
                fecha_alta: $scope.fecha_alta,
                id_tipousuario: $scope.tipousuario
            };
            $http({
                method: 'GET',
                url: 'http://localhost:8081/barberia66/barberia66?ob=usuario&op=update',
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