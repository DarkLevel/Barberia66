/* global moduleCommon */

'use strict';

moduleCommon.controller('operacionesController', ['$scope', 'toolService', 'sessionService', '$location', '$anchorScroll',
    function ($scope, toolService, oSessionService, $location, $anchorScroll) {
        $anchorScroll();
        
        $scope.isAdmin = oSessionService.isAdmin();
        $scope.isBoss = oSessionService.isBoss();
        $scope.isActive = toolService.isActive;
        $scope.enlace = function (enlace) {
            switch (enlace) {
                case "estadocita":
                    $location.url('estadocita/plist/');
                    break;
                case "producto":
                    $location.url('producto/plist/');
                    break;
                case "tipocita":
                    $location.url('tipocita/plist/');
                    break;
                case "tipoproducto":
                    $location.url('tipoproducto/plist/');
                    break;
                case "tiporegistro":
                    $location.url('tiporegistro/plist/');
                    break;
                case "tipousuario":
                    $location.url('tipousuario/plist/');
                    break;
                case "usuario":
                    $location.url('usuario/plist/');
                    break;
            }
        };
    }
]);