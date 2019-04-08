/* global barberia66 */

var autenticacionAdmin = function ($q, $location, $http, sessionService) {
    var deferred = $q.defer();
    $http({
        method: 'GET',
        url: 'http://localhost:8081/barberia66/barberia66?ob=usuario&op=check'
    }).then(function (response) {
        if (response.data.status === 200) {
            if (response.data.message.obj_tipousuario.id === 1) {
                sessionService.setSessionActive();
                sessionService.setAdminActive();
                sessionService.setBossInactive();
                sessionService.setWorkerInactive();
                sessionService.setUserName(response.data.message.username);
                sessionService.setId(response.data.message.id);
                deferred.resolve();
            }
        } else {
            sessionService.setSessionInactive();
            sessionService.setAdminInactive();
            sessionService.setBossInactive();
            sessionService.setWorkerInactive();
            $location.path('/home');
        }
    }, function () {
        sessionService.setSessionInactive();
        sessionService.setAdminInactive();
        sessionService.setBossInactive();
        sessionService.setWorkerInactive();
        $location.path('/home');
    });
    return deferred.promise;
};

var autenticacionJefe = function ($q, $location, $http, sessionService) {
    var deferred = $q.defer();
    $http({
        method: 'GET',
        url: 'http://localhost:8081/barberia66/barberia66?ob=usuario&op=check'
    }).then(function (response) {
        if (response.data.status === 200) {
            if (response.data.message.obj_tipousuario.id === 1) {
                sessionService.setSessionActive();
                sessionService.setAdminActive();
                sessionService.setBossInactive();
                sessionService.setWorkerInactive();
                sessionService.setUserName(response.data.message.username);
                sessionService.setId(response.data.message.id);
                deferred.resolve();
            }
            if (response.data.message.obj_tipousuario.id === 2) {
                sessionService.setSessionActive();
                sessionService.setAdminInactive();
                sessionService.setBossActive();
                sessionService.setWorkerInactive();
                sessionService.setUserName(response.data.message.username);
                sessionService.setId(response.data.message.id);
                deferred.resolve();
            }
        } else {
            sessionService.setSessionInactive();
            sessionService.setAdminInactive();
            sessionService.setBossInactive();
            sessionService.setWorkerInactive();
            $location.path('/home');
        }
    }, function () {
        sessionService.setSessionInactive();
        sessionService.setAdminInactive();
        sessionService.setBossInactive();
        sessionService.setWorkerInactive();
        $location.path('/home');
    });
    return deferred.promise;
};

var autenticacionEmpleado = function ($q, $location, $http, sessionService) {
    var deferred = $q.defer();
    $http({
        method: 'GET',
        url: 'http://localhost:8081/barberia66/barberia66?ob=usuario&op=check'
    }).then(function (response) {
        if (response.data.status === 200) {
            if (response.data.message.obj_tipousuario.id === 1) {
                sessionService.setSessionActive();
                sessionService.setAdminActive();
                sessionService.setBossInactive();
                sessionService.setWorkerInactive();
                sessionService.setUserName(response.data.message.username);
                sessionService.setId(response.data.message.id);
            }
            if (response.data.message.obj_tipousuario.id === 2) {
                sessionService.setSessionActive();
                sessionService.setAdminInactive();
                sessionService.setBossActive();
                sessionService.setWorkerInactive();
                sessionService.setUserName(response.data.message.username);
                sessionService.setId(response.data.message.id);
            }
            if (response.data.message.obj_tipousuario.id === 3) {
                sessionService.setSessionActive();
                sessionService.setAdminInactive();
                sessionService.setBossInactive();
                sessionService.setWorkerActive();
                sessionService.setUserName(response.data.message.username);
                sessionService.setId(response.data.message.id);
            }
            deferred.resolve();
        } else {
            sessionService.setSessionInactive();
            sessionService.setAdminInactive();
            sessionService.setBossInactive();
            sessionService.setWorkerInactive();
            $location.path('/home');
        }
    }, function () {
        sessionService.setSessionInactive();
        sessionService.setAdminInactive();
        sessionService.setBossInactive();
        sessionService.setWorkerInactive();
        $location.path('/home');
    });
    return deferred.promise;
};

var all = function ($q, $location, $http, sessionService) {
    var deferred = $q.defer();
    $http({
        method: 'GET',
        url: 'http://localhost:8081/barberia66/barberia66?ob=usuario&op=check'
    }).then(function (response) {
        if (response.data.status === 200) {
            if (response.data.message.obj_tipousuario.id === 1) {
                sessionService.setSessionActive();
                sessionService.setAdminActive();
                sessionService.setBossInactive();
                sessionService.setWorkerInactive();
                sessionService.setUserName(response.data.message.username);
                sessionService.setId(response.data.message.id);
            }
            if (response.data.message.obj_tipousuario.id === 2) {
                sessionService.setSessionActive();
                sessionService.setAdminInactive();
                sessionService.setBossActive();
                sessionService.setWorkerInactive();
                sessionService.setUserName(response.data.message.username);
                sessionService.setId(response.data.message.id);
            }
            if (response.data.message.obj_tipousuario.id === 3) {
                sessionService.setSessionActive();
                sessionService.setAdminInactive();
                sessionService.setBossInactive();
                sessionService.setWorkerActive();
                sessionService.setUserName(response.data.message.username);
                sessionService.setId(response.data.message.id);
            }
            deferred.resolve();
        } else {
            sessionService.setSessionInactive();
            sessionService.setAdminInactive();
            sessionService.setBossInactive();
            sessionService.setWorkerInactive();
            deferred.resolve();
        }
    }, function () {
        sessionService.setSessionInactive();
        sessionService.setAdminInactive();
        sessionService.setBossInactive();
        sessionService.setWorkerInactive();
        $location.path('/home');
    });
    return deferred.promise;
};

barberia66.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/home', {templateUrl: 'js/app/common/home.html', controller: 'homeController', resolve: {auth: all}});

        $routeProvider.when('/operaciones', {templateUrl: 'js/app/common/operaciones.html', controller: 'operacionesController', resolve: {auth: autenticacionJefe}});

        $routeProvider.when('/comercio/venta', {templateUrl: 'js/app/comercio/venta.html', controller: 'comercioVentaController', resolve: {auth: autenticacionEmpleado}});
        $routeProvider.when('/comercio/compra', {templateUrl: 'js/app/comercio/compra.html', controller: 'comercioCompraController', resolve: {auth: autenticacionEmpleado}});
        $routeProvider.when('/comercio/uso', {templateUrl: 'js/app/comercio/uso.html', controller: 'comercioUsoController', resolve: {auth: autenticacionEmpleado}});
        
        $routeProvider.when('/cita/calendario', {templateUrl: 'js/app/cita/calendario.html', controller: 'citaCalendarioController', resolve: {auth: autenticacionEmpleado}});
        
        $routeProvider.when('/estadocita/create', {templateUrl: 'js/app/estadocita/create.html', controller: 'estadocitaCreateController', resolve: {auth: autenticacionJefe}});
        $routeProvider.when('/estadocita/plist/:rpp?/:page?/:order?', {templateUrl: 'js/app/estadocita/plist.html', controller: 'estadocitaPlistController', resolve: {auth: autenticacionJefe}});
        $routeProvider.when('/estadocita/remove/:id?', {templateUrl: 'js/app/estadocita/remove.html', controller: 'estadocitaRemoveController', resolve: {auth: autenticacionJefe}});
        $routeProvider.when('/estadocita/update/:id?', {templateUrl: 'js/app/estadocita/update.html', controller: 'estadocitaUpdateController', resolve: {auth: autenticacionJefe}});
        $routeProvider.when('/estadocita/view/:id?', {templateUrl: 'js/app/estadocita/view.html', controller: 'estadocitaViewController', resolve: {auth: autenticacionJefe}});
        
        $routeProvider.when('/producto/create', {templateUrl: 'js/app/producto/create.html', controller: 'productoCreateController', resolve: {auth: autenticacionJefe}});
        $routeProvider.when('/producto/plist/:rpp?/:page?/:order?', {templateUrl: 'js/app/producto/plist.html', controller: 'productoPlistController', resolve: {auth: autenticacionJefe}});
        $routeProvider.when('/producto/remove/:id?', {templateUrl: 'js/app/producto/remove.html', controller: 'productoRemoveController', resolve: {auth: autenticacionJefe}});
        $routeProvider.when('/producto/update/:id?', {templateUrl: 'js/app/producto/update.html', controller: 'productoUpdateController', resolve: {auth: autenticacionJefe}});
        $routeProvider.when('/producto/view/:id?', {templateUrl: 'js/app/producto/view.html', controller: 'productoViewController', resolve: {auth: autenticacionJefe}});
        
        //$routeProvider.when('/registro/calendario', {templateUrl: 'js/app/registro/calendario.html', controller: 'registroCalendarioController', resolve: {auth: autenticacionEmpleado}});
        
        $routeProvider.when('/tipocita/create', {templateUrl: 'js/app/tipocita/create.html', controller: 'tipocitaCreateController', resolve: {auth: autenticacionJefe}});
        $routeProvider.when('/tipocita/plist/:rpp?/:page?/:order?', {templateUrl: 'js/app/tipocita/plist.html', controller: 'tipocitaPlistController', resolve: {auth: autenticacionJefe}});
        $routeProvider.when('/tipocita/remove/:id?', {templateUrl: 'js/app/tipocita/remove.html', controller: 'tipocitaRemoveController', resolve: {auth: autenticacionJefe}});
        $routeProvider.when('/tipocita/update/:id?', {templateUrl: 'js/app/tipocita/update.html', controller: 'tipocitaUpdateController', resolve: {auth: autenticacionJefe}});
        $routeProvider.when('/tipocita/view/:id?', {templateUrl: 'js/app/tipocita/view.html', controller: 'tipocitaViewController', resolve: {auth: autenticacionJefe}});
        
        $routeProvider.when('/tipoproducto/create', {templateUrl: 'js/app/tipoproducto/create.html', controller: 'tipoproductoCreateController', resolve: {auth: autenticacionJefe}});
        $routeProvider.when('/tipoproducto/plist/:rpp?/:page?/:order?', {templateUrl: 'js/app/tipoproducto/plist.html', controller: 'tipoproductoPlistController', resolve: {auth: autenticacionJefe}});
        $routeProvider.when('/tipoproducto/remove/:id?', {templateUrl: 'js/app/tipoproducto/remove.html', controller: 'tipoproductoRemoveController', resolve: {auth: autenticacionJefe}});
        $routeProvider.when('/tipoproducto/update/:id?', {templateUrl: 'js/app/tipoproducto/update.html', controller: 'tipoproductoUpdateController', resolve: {auth: autenticacionJefe}});
        $routeProvider.when('/tipoproducto/view/:id?', {templateUrl: 'js/app/tipoproducto/view.html', controller: 'tipoproductoViewController', resolve: {auth: autenticacionJefe}});
        
        $routeProvider.when('/tiporegistro/create', {templateUrl: 'js/app/tiporegistro/create.html', controller: 'tiporegistroCreateController', resolve: {auth: autenticacionAdmin}});
        $routeProvider.when('/tiporegistro/plist/:rpp?/:page?/:order?', {templateUrl: 'js/app/tiporegistro/plist.html', controller: 'tiporegistroPlistController', resolve: {auth: autenticacionAdmin}});
        $routeProvider.when('/tiporegistro/remove/:id?', {templateUrl: 'js/app/tiporegistro/remove.html', controller: 'tiporegistroRemoveController', resolve: {auth: autenticacionAdmin}});
        $routeProvider.when('/tiporegistro/update/:id?', {templateUrl: 'js/app/tiporegistro/update.html', controller: 'tiporegistroUpdateController', resolve: {auth: autenticacionAdmin}});
        $routeProvider.when('/tiporegistro/view/:id?', {templateUrl: 'js/app/tiporegistro/view.html', controller: 'tiporegistroViewController', resolve: {auth: autenticacionAdmin}});
        
        $routeProvider.when('/tipousuario/create', {templateUrl: 'js/app/tipousuario/create.html', controller: 'tipousuarioCreateController', resolve: {auth: autenticacionAdmin}});
        $routeProvider.when('/tipousuario/plist/:rpp?/:page?/:order?', {templateUrl: 'js/app/tipousuario/plist.html', controller: 'tipousuarioPlistController', resolve: {auth: autenticacionAdmin}});
        $routeProvider.when('/tipousuario/remove/:id?', {templateUrl: 'js/app/tipousuario/remove.html', controller: 'tipousuarioRemoveController', resolve: {auth: autenticacionAdmin}});
        $routeProvider.when('/tipousuario/update/:id?', {templateUrl: 'js/app/tipousuario/update.html', controller: 'tipousuarioUpdateController', resolve: {auth: autenticacionAdmin}});
        $routeProvider.when('/tipousuario/view/:id?', {templateUrl: 'js/app/tipousuario/view.html', controller: 'tipousuarioViewController', resolve: {auth: autenticacionAdmin}});
        
        $routeProvider.when('/usuario/create', {templateUrl: 'js/app/usuario/create.html', controller: 'usuarioCreateController', resolve: {auth: autenticacionJefe}});
        $routeProvider.when('/usuario/plist/:rpp?/:page?/:order?', {templateUrl: 'js/app/usuario/plist.html', controller: 'usuarioPlistController', resolve: {auth: autenticacionJefe}});
        $routeProvider.when('/usuario/remove/:id?', {templateUrl: 'js/app/usuario/remove.html', controller: 'usuarioRemoveController', resolve: {auth: autenticacionJefe}});
        $routeProvider.when('/usuario/update/:id?', {templateUrl: 'js/app/usuario/update.html', controller: 'usuarioUpdateController', resolve: {auth: autenticacionJefe}});
        $routeProvider.when('/usuario/view/:id?', {templateUrl: 'js/app/usuario/view.html', controller: 'usuarioViewController', resolve: {auth: autenticacionJefe}});

        $routeProvider.otherwise({redirectTo: '/home'});
    }]);