/* global moduleProducto */

'use strict';

moduleProducto.controller('productoUpdateController', ['$scope', '$http', 'toolService', '$routeParams', '$anchorScroll',
    function ($scope, $http, toolService, $routeParams, $anchorScroll) {
        $anchorScroll();

        $scope.formulario = true;
        $scope.botones = true;
        $scope.correcto = false;

        $http({
            method: 'GET',
            url: 'http://localhost:8081/barberia66/barberia66?ob=producto&op=get&id=' + $routeParams.id
        }).then(function (response) {
            $scope.status = response.status;
            $scope.id = response.data.message.id;
            $scope.nombre = response.data.message.nombre;
            $scope.descripcion = response.data.message.descripcion;
            $scope.existencias = response.data.message.existencias;
            $scope.precio = response.data.message.precio;
            $scope.iva = response.data.message.iva;
            $scope.foto = response.data.message.foto;
        }, function (response) {
            $scope.status = response.status;
            $scope.ajaxData = response.data.message || 'Request failed';
        });
        
        $http({
            method: 'GET',
            url: 'http://localhost:8081/barberia66/barberia66?ob=tipoproducto&op=getpage&rpp=10000&page=1&order=descripcion,asc'
        }).then(function (response) {
            $scope.ajaxDataTipoProducto = response.data.message;
            var listatipoproductos = [];
            $scope.ajaxDataTipoProducto.forEach(element => {
                var tipoproductos = {
                    obj_tipoproducto: element
                };
                listatipoproductos.push(tipoproductos);
            });
            $scope.tipoproducto = listatipoproductos[0].obj_tipoproducto.id;
        }, function (response) {
            $scope.status = response.status;
            $scope.ajaxDataTipoProducto = response.data.message || 'Request failed';
        });

        $scope.volver = function () {
            window.history.back();
        };

        $scope.editar = function () {
            var foto;
            if ($scope.myFile !== undefined) {
                //Si el nombre de la imagen es "Default.png" significa que es la de por defecto, se le deja intacta
                if ($scope.myFile.name === "Default.png") {
                    foto = $scope.myFile.name;
                    //Si la imagen que tenía el producto era la predefinida y me suben una nueva foto diferente.
                } else if ($scope.foto === "Default.png" && $scope.myFile.name !== "Default.png") {
                    foto = guid() + $scope.myFile.name;
                } else {
                    foto = $scope.foto;
                }
                uploadPhoto(foto);
            } else {
                foto = $scope.foto;
            }

            var json = {
                id: $scope.id,
                nombre: $scope.nombre,
                descripcion: $scope.descripcion,
                existencias: $scope.existencias,
                precio: $scope.precio,
                iva: $scope.iva,
                foto: foto,
                id_tipoproducto: $scope.tipoproducto
            };
            $http({
                method: 'GET',
                url: 'http://localhost:8081/barberia66/barberia66?ob=producto&op=update',
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

        function reemplazar(precio) {
            var precioString = precio.toString();
            var precioCambiado = precioString.replace(".", ",");

            return precioCambiado;
        }

        function uploadPhoto(name) {
            //Solucion mas cercana
            //https://stackoverflow.com/questions/37039852/send-formdata-with-other-field-in-angular
            var file = $scope.myFile;
            file = new File([file], name, {type: file.type});
            //Api FormData 
            //https://developer.mozilla.org/es/docs/Web/API/XMLHttpRequest/FormData
            var oFormData = new FormData();
            oFormData.append('file', file);
            $http({
                headers: {'Content-Type': undefined},
                method: 'POST',
                data: oFormData,
                url: `http://localhost:8081/trolleyes/json?ob=producto&op=addimage`
            });
        }

        function guid() {
            return "ss-s-s-s-sss".replace(/s/g, s4);
        }

        function s4() {
            return Math.floor((1 + Math.random()) * 0x10000)
                    .toString(16)
                    .substring(1);
        }
    }
]).directive('fileModel', ['$parse', function ($parse) {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
                var model = $parse(attrs.fileModel);
                var modelSetter = model.assign;

                element.bind('change', function () {
                    scope.$apply(function () {
                        modelSetter(scope, element[0].files[0]);
                    });
                });
            }
        };
    }
]);