/* global moduleProducto */

'use strict';

moduleProducto.controller('productoCreateController', ['$scope', '$http', 'toolService', '$anchorScroll', '$location',
    function ($scope, $http, toolService, $anchorScroll, $location) {
        $anchorScroll();

        $scope.formulario = true;
        $scope.botones = true;
        $scope.correcto = false;
        $scope.fallo = false;

        $http({
            method: 'GET',
            url: 'http://localhost:8081/barberia66/barberia66?ob=tipoproducto&op=getpage&rpp=10000&page=1&order=descripcion,asc'
        }).then(function (response) {
            $scope.ajaxDataTipoProducto = response.data.message;
            $scope.tipoproducto = $scope.ajaxDataTipoProducto[0].id;
        }, function (response) {
            $scope.status = response.status;
            $scope.ajaxDataTipoProducto = response.data.message || 'Request failed';
        });

        $scope.volver = function () {
            $location.url('producto/plist');
        };

        $scope.crear = function () {
            if ($scope.myFile === undefined) {
                $scope.foto = "Default.png";
            } else {
                $scope.foto = guid() + $scope.myFile.name;
                uploadPhoto($scope.foto);
            }

            var json = {
                nombre: $scope.nombre,
                descripcion: $scope.descripcion,
                precio: ($scope.precio).replace(",", "."),
                existencias: $scope.existencias,
                iva: $scope.iva,
                foto: $scope.foto,
                id_tipoproducto: $scope.tipoproducto
            };
            $http({
                method: 'GET',
                url: 'http://localhost:8081/barberia66/barberia66?ob=producto&op=create',
                params: {json: JSON.stringify(json)}
            }).then(function (response) {
                $scope.status = response.status;
                $scope.ajaxData = response.data.message;
                if ($scope.status === 200) {
                    $scope.formulario = false;
                    $scope.botones = false;
                    $scope.fallo = false;
                    $scope.correcto = true;
                } else {
                    $scope.fallo = true;
                }
            }, function (response) {
                $scope.status = response.status;
            });
        };

        $scope.isActive = toolService.isActive;

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