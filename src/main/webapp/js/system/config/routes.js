/* global barberia66 */

var autenticacionAdministrador = function ($q, $location, $http, sessionService, countCarritoService) {
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
                countCarritoService.updateCarrito();
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


var autenticacionJefe = function ($q, $location, $http, sessionService, countCarritoService) {
    var deferred = $q.defer();
    $http({
        method: 'GET',
        url: 'http://localhost:8081/barberia66/barberia66?ob=usuario&op=check'
    }).then(function (response) {
        if (response.data.status === 200) {
            if (response.data.message.obj_tipousuario.id === 2) {
                sessionService.setSessionActive();
                sessionService.setAdminInactive();
                sessionService.setBossActive();
                sessionService.setWorkerInactive();
                sessionService.setUserName(response.data.message.username);
                sessionService.setId(response.data.message.id);
                countCarritoService.updateCarrito();
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

var autenticacionEmpleado = function ($q, $location, $http, sessionService, countCarritoService) {
    var deferred = $q.defer();
    $http({
        method: 'GET',
        url: 'http://localhost:8081/barberia66/barberia66?ob=usuario&op=check'
    }).then(function (response) {
        if (response.data.status === 200) {
            if (response.data.message.obj_tipousuario.id === 3) {
                sessionService.setSessionActive();
                sessionService.setAdminInactive();
                sessionService.setBossInactive();
                sessionService.setWorkerActive();
                sessionService.setUserName(response.data.message.username);
                sessionService.setId(response.data.message.id);
                countCarritoService.updateCarrito();
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

var logged = function ($q, $location, $http, sessionService, countCarritoService) {
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
                countCarritoService.updateCarrito();
            }
            if (response.data.message.obj_tipousuario.id === 2) {
                sessionService.setSessionActive();
                sessionService.setAdminInactive();
                sessionService.setBossActive();
                sessionService.setWorkerInactive();
                sessionService.setUserName(response.data.message.username);
                sessionService.setId(response.data.message.id);
                countCarritoService.updateCarrito();
            }
            if (response.data.message.obj_tipousuario.id === 3) {
                sessionService.setSessionActive();
                sessionService.setAdminInactive();
                sessionService.setBossInactive();
                sessionService.setWorkerActive();
                sessionService.setUserName(response.data.message.username);
                sessionService.setId(response.data.message.id);
                countCarritoService.updateCarrito();
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

var all = function ($q, $location, $http, sessionService, countCarritoService) {
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
                countCarritoService.updateCarrito();
            }
            if (response.data.message.obj_tipousuario.id === 2) {
                sessionService.setSessionActive();
                sessionService.setAdminInactive();
                sessionService.setBossActive();
                sessionService.setWorkerInactive();
                sessionService.setUserName(response.data.message.username);
                sessionService.setId(response.data.message.id);
                countCarritoService.updateCarrito();
            }
            if (response.data.message.obj_tipousuario.id === 3) {
                sessionService.setSessionActive();
                sessionService.setAdminInactive();
                sessionService.setBossInactive();
                sessionService.setWorkerActive();
                sessionService.setUserName(response.data.message.username);
                sessionService.setId(response.data.message.id);
                countCarritoService.updateCarrito();
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

        $routeProvider.when('/operaciones', {templateUrl: 'js/app/common/operaciones.html', controller: 'operacionesController', resolve: {auth: logged}});

        $routeProvider.when('/cita/plist', {templateUrl: 'js/app/cita/plist.html', controller: 'citaPlistController', resolve: {auth: logged}});
        
        $routeProvider.when('/estadocita/plist', {templateUrl: 'js/app/estadocita/plist.html', controller: 'estadocitaPlistController', resolve: {auth: logged}});
        
        $routeProvider.when('/linea/plist', {templateUrl: 'js/app/linea/plist.html', controller: 'lineaPlistController', resolve: {auth: logged}});
        
        $routeProvider.when('/producto/plist', {templateUrl: 'js/app/producto/plist.html', controller: 'productoPlistController', resolve: {auth: logged}});
        
        $routeProvider.when('/registro/plist', {templateUrl: 'js/app/registro/plist.html', controller: 'registroPlistController', resolve: {auth: logged}});
        
        $routeProvider.when('/tipocita/plist', {templateUrl: 'js/app/tipocita/plist.html', controller: 'tipocitaPlistController', resolve: {auth: logged}});
        
        $routeProvider.when('/tipoproducto/plist', {templateUrl: 'js/app/tipoproducto/plist.html', controller: 'tipoproductoPlistController', resolve: {auth: logged}});
        
        $routeProvider.when('/tiporegistro/plist', {templateUrl: 'js/app/tiporegistro/plist.html', controller: 'tiporegistroPlistController', resolve: {auth: logged}});
        
        $routeProvider.when('/tipousuario/plist', {templateUrl: 'js/app/tipousuario/plist.html', controller: 'tipousuarioPlistController', resolve: {auth: logged}});
        
        $routeProvider.when('/usuario/plist', {templateUrl: 'js/app/usuario/plist.html', controller: 'usuarioPlistController', resolve: {auth: logged}});

        $routeProvider.otherwise({redirectTo: '/home'});
    }]);