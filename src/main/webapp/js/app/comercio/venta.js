/* global moduleComercio */

'use strict';

moduleComercio.controller('comercioVentaController', ['$scope', 'toolService', '$http',
    function ($scope, toolService, $http) {
        
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

        $scope.substract = function (producto) {
            if (producto.cantidad > 0){
                producto.producto.existencias++;
                producto.cantidad--;
            }
        };

        $scope.add = function (producto) {
            if (producto.producto.existencias > 0){
                producto.producto.existencias--;
                producto.cantidad++;
            }
        };
        
        $scope.vender = function (){
            $scope.productosSeleccionados = [];
            for (var i = 0; i < $scope.productos.length; i++){
                if ($scope.productos[i].cantidad !== 0){
                    $scope.productosSeleccionados.push($scope.productos[i]);
                }
            }
            if($scope.productosSeleccionados.length === 0){
                $scope.alertaproductos = true;
            } else{
                $scope.alertaproductos = false;
            }
        };

        function reemplazar(precio) {
            return precio.toString().replace(".", ",");
        }
        
        $scope.isActive = toolService.isActive;
    }
]);