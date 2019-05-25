/* global moduleTipocita */

'use strict';

moduleTipocita.controller('tipocitaRemoveController', ['$scope', '$http', 'toolService', '$routeParams', '$anchorScroll', '$location',
    function ($scope, $http, toolService, $routeParams, $anchorScroll, $location) {
        $anchorScroll();
        
        $scope.botones = true;
        $scope.alerta = false;
        $scope.formulario = true;

        if (!$routeParams.id) {
            $scope.id = 1;
        } else {
            $scope.id = $routeParams.id;
        }

        $http({
            method: 'GET',
            url: 'http://localhost:8081/barberia66/barberia66?ob=tipocita&op=get&id=' + $scope.id
        }).then(function (response) {
            $scope.status = response.status;
            $scope.ajaxData = response.data.message;
        }, function (response) {
            $scope.status = response.status;
            $scope.ajaxData = response.data.message || 'Request failed';
        });

        $scope.volver = function () {
            $location.url('tipocita/plist');
        };

        $scope.borrar = function () {
            $http({
                method: 'GET',
                url: 'http://localhost:8081/barberia66/barberia66?ob=tipocita&op=remove&id=' + $scope.id
            }).then(function (response) {
                $scope.status = response.status;
                $scope.ajaxData = response.data.message;
                $scope.formulario = false;
                $scope.botones = false;
                $scope.alerta = true;
            }, function (response) {
                $scope.status = response.status;
                $scope.ajaxData = response.data.message || 'Request failed';
            });
        };

        $scope.isActive = toolService.isActive;
    }
]);