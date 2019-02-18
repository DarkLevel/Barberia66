/* global moduleComercio */

'use strict';

moduleComercio.controller('comercioUsoController', ['$scope', 'toolService', '$http', '$mdDialog', '$anchorScroll',
    function ($scope, toolService, $http, $mdDialog, $anchorScroll) {
        $anchorScroll();

        $scope.alertaproductos = false;

        $http({
            method: 'GET',
            url: 'http://localhost:8081/barberia66/barberia66?ob=producto&op=getpage&rpp=10000&page=1'
        }).then(function (response) {
            for (var i = 0; i < response.data.message.length; i++) {
                response.data.message[i].precio = reemplazar(response.data.message[i].precio);
            }
            $scope.productos = [];
            response.data.message.forEach(element => {
                var producto = {
                    producto: element,
                    cantidad: 0
                };
                $scope.productos.push(producto);
            });
        }, function (response) {
            $scope.status = response.status;
            response.data.message = response.data.message || 'Request failed';
        });

        $http({
            method: 'GET',
            url: 'http://localhost:8081/barberia66/barberia66?ob=usuario&op=getpage&rpp=10000&page=1'
        }).then(function (response) {
            $scope.usuarios = [];
            response.data.message.forEach(element => {
                if (element.obj_tipousuario.id > 1) {
                    var usuario = {
                        id: element.id,
                        nombre_completo: element.nombre + ' ' + element.apellido1 + ' ' + element.apellido2
                    };
                    $scope.usuarios.push(usuario);
                }
            });
        }, function (response) {
            $scope.status = response.status;
            response.data.message = response.data.message || 'Request failed';
        });

        $scope.substract = function (producto) {
            if (producto.cantidad > 0) {
                producto.producto.existencias++;
                producto.cantidad--;
            }
        };

        $scope.add = function (producto) {
            if (producto.producto.existencias > 0) {
                producto.producto.existencias--;
                producto.cantidad++;
            }
        };

        $scope.usar = function (event) {
            $scope.productosSeleccionados = [];
            for (var i = 0; i < $scope.productos.length; i++) {
                if ($scope.productos[i].cantidad !== 0) {
                    $scope.productosSeleccionados.push($scope.productos[i]);
                }
            }
            if ($scope.productosSeleccionados.length === 0) {
                $scope.alertaproductos = true;
            } else {
                $anchorScroll();
                $scope.alertaproductos = false;
                $scope.usuario = $scope.usuarios[0].id;
                $mdDialog.show({
                    controller: DialogController,
                    templateUrl: 'js/app/comercio/usoDialog.html',
                    parent: angular.element(document.body),
                    targetEvent: event,
                    clickOutsideToClose: true,
                    fullscreen: DialogController.customFullscreen,
                    scope: $scope,
                    preserveScope: true
                }).then(function () {
                    $scope.statusDialog = 'ok';
                }, function () {
                    $scope.statusDialog = 'cancel';
                });
            }
        };

        function DialogController($scope, $mdDialog) {
            $scope.cancel = function () {
                $mdDialog.cancel();
            };
            $scope.answer = function () {
                var productos = [];
                for (var i = 0; i < $scope.productosSeleccionados.length; i++) {
                    var producto = {
                        id: $scope.productosSeleccionados[i].producto.id,
                        cantidad: $scope.productosSeleccionados[i].cantidad
                    };
                    productos.push(producto);
                }
                $http({
                    method: 'GET',
                    url: 'http://localhost:8081/barberia66/barberia66?ob=comercio&op=uso&id=' + $scope.usuario,
                    params: {productos: angular.toJson(productos)}
                }).then(function (response) {
                    if (response.data.status === 200) {
                        for (var i = 0; i < response.data.message.length; i++) {
                            response.data.message[i].precio = reemplazar(response.data.message[i].precio);
                        }
                        $scope.productos = [];
                        response.data.message.forEach(element => {
                            var producto = {
                                producto: element,
                                cantidad: 0
                            };
                            $scope.productos.push(producto);
                        });
                    }
                }, function (response) {
                    $scope.status = response.status;
                    response.data.message = response.data.message || 'Request failed';
                });
                $mdDialog.hide();
            };
        }

        function reemplazar(precio) {
            return precio.toString().replace(".", ",");
        }

        $scope.isActive = toolService.isActive;
    }
]);