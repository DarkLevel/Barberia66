/* global moment */

'use strict';

var barberia66 = angular.module('MyApp', [
    'ngRoute',
    'ngMaterial',
    'services',
    'comercioControllers',
    'components',
    'commonControllers',
    'citaControllers',
    'estadocitaControllers',
    'gestionproductosControllers',
    'lineaControllers',
    'productoControllers',
    'registroControllers',
    'tipocitaControllers',
    'tipoproductoControllers',
    'tiporegistroControllers',
    'tipousuarioControllers',
    'usuarioControllers'
]).config(function ($mdDateLocaleProvider) {
    // Example of a Spanish localization.
    $mdDateLocaleProvider.months = ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'];
    $mdDateLocaleProvider.shortMonths = ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'];
    $mdDateLocaleProvider.days = ['Domingo', 'Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sabado'];
    $mdDateLocaleProvider.shortDays = ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sa'];

    // Can change week display to start on Monday.
    $mdDateLocaleProvider.firstDayOfWeek = 1;

    //In addition to date display, date components also need localized messages for aria-labels for screen-reader users.
    $mdDateLocaleProvider.weekNumberFormatter = function (weekNumber) {
        return 'Semana ' + weekNumber;
    };

    $mdDateLocaleProvider.msgCalendar = 'Calendario';
    $mdDateLocaleProvider.msgOpenCalendar = 'Abrir calendario';

    $mdDateLocaleProvider.formatDate = function (date) {
        return moment(date).format('DD-MM-YYYY');
    };
});

var moduleService = angular.module('services', []);
var moduleComercio = angular.module('comercioControllers', []);
var moduleComponent = angular.module('components', []);
var moduleCommon = angular.module('commonControllers', []);
var moduleCita = angular.module('citaControllers', []);
var moduleEstadocita = angular.module('estadocitaControllers', []);
var moduleGestionproductos = angular.module('gestionproductosControllers', []);
var moduleLinea = angular.module('lineaControllers', []);
var moduleProducto = angular.module('productoControllers', []);
var moduleRegistro = angular.module('registroControllers', []);
var moduleTipocita = angular.module('tipocitaControllers', []);
var moduleTipoproducto = angular.module('tipoproductoControllers', []);
var moduleTiporegistro = angular.module('tiporegistroControllers', []);
var moduleTipousuario = angular.module('tipousuarioControllers', []);
var moduleUsuario = angular.module('usuarioControllers', []);
