/* global barberia66 */

var autenticacionAdministrador = function ($q, $location, $http, sessionService, countCarritoService) {
    var deferred = $q.defer();
    $http({
        method: 'GET',
        url: 'http://localhost:8081/barberia66/barberia66?ob=usuario&op=check'
    }).then(function (response) {
        if (response.data.status === 200) {
            if (response.data.message.obj_tipoUsuario.id === 1) {
                sessionService.setSessionActive();
                sessionService.setAdminActive();
                sessionService.setClientInactive();
                sessionService.setUserName(response.data.message.login);
                sessionService.setId(response.data.message.id);
                countCarritoService.updateCarrito();
                deferred.resolve();
            }
        } else {
            sessionService.setSessionInactive();
            sessionService.setAdminInactive();
            sessionService.setClientInactive();
            $location.path('/home');
        }
    }, function () {
        sessionService.setSessionInactive();
        sessionService.setAdminInactive();
        sessionService.setClientInactive();
        $location.path('/home');
    });
    return deferred.promise;
};


var autenticacionCliente = function ($q, $location, $http, sessionService, countCarritoService) {
    var deferred = $q.defer();
    $http({
        method: 'GET',
        url: 'http://localhost:8081/barberia66/barberia66?ob=usuario&op=check'
    }).then(function (response) {
        if (response.data.status === 200) {
            if (response.data.message.obj_tipoUsuario.id === 2) {
                sessionService.setSessionActive();
                sessionService.setAdminInactive();
                sessionService.setClientActive();
                sessionService.setUserName(response.data.message.login);
                sessionService.setId(response.data.message.id);
                countCarritoService.updateCarrito();
                deferred.resolve();
            }
        } else {
            sessionService.setSessionInactive();
            sessionService.setAdminInactive();
            sessionService.setClientInactive();
            $location.path('/home');
        }
    }, function () {
        sessionService.setSessionInactive();
        sessionService.setAdminInactive();
        sessionService.setClientInactive();
        $location.path('/home');
    });
    return deferred.promise;
};

var autenticacionAny = function ($q, $location, $http, sessionService, countCarritoService) {
    var deferred = $q.defer();
    $http({
        method: 'GET',
        url: 'http://localhost:8081/barberia66/barberia66?ob=usuario&op=check'
    }).then(function (response) {
        if (response.data.status === 200) {
            if (response.data.message.obj_tipoUsuario.id === 1) {
                sessionService.setSessionActive();
                sessionService.setAdminActive();
                sessionService.setClientInactive();
                sessionService.setUserName(response.data.message.login);
                sessionService.setId(response.data.message.id);
                countCarritoService.updateCarrito();
            }
            if (response.data.message.obj_tipoUsuario.id === 2) {
                sessionService.setSessionActive();
                sessionService.setAdminInactive();
                sessionService.setClientActive();
                sessionService.setUserName(response.data.message.login);
                sessionService.setId(response.data.message.id);
                countCarritoService.updateCarrito();
            }
            deferred.resolve();
        } else {
            sessionService.setSessionInactive();
            sessionService.setAdminInactive();
            sessionService.setClientInactive();
            $location.path('/home');
        }
    }, function () {
        sessionService.setSessionInactive();
        sessionService.setAdminInactive();
        sessionService.setClientInactive();
        $location.path('/home');
    });
    return deferred.promise;
};

var noAutenticacion = function ($q, $location, $http, sessionService) {
    var deferred = $q.defer();
    $http({
        method: 'GET',
        url: 'http://localhost:8081/barberia66/barberia66?ob=usuario&op=check'
    }).then(function (response) {
        if (response.data.status === 401) {
            sessionService.setSessionInactive();
            sessionService.setAdminInactive();
            sessionService.setClientInactive();
            deferred.resolve();
        }
    }, function () {
        sessionService.setSessionInactive();
        sessionService.setAdminInactive();
        sessionService.setClientInactive();
        $location.path('/home');
    });
    return deferred.promise;
};

var everyone = function ($q, $location, $http, sessionService, countCarritoService) {
    var deferred = $q.defer();
    $http({
        method: 'GET',
        url: 'http://localhost:8081/barberia66/barberia66?ob=usuario&op=check'
    }).then(function (response) {
        if (response.data.status === 200) {
            if (response.data.message.obj_tipoUsuario.id === 1) {
                sessionService.setSessionActive();
                sessionService.setAdminActive();
                sessionService.setClientInactive();
                sessionService.setUserName(response.data.message.login);
                sessionService.setId(response.data.message.id);
                countCarritoService.updateCarrito();
            }
            if (response.data.message.obj_tipoUsuario.id === 2) {
                sessionService.setSessionActive();
                sessionService.setAdminInactive();
                sessionService.setClientActive();
                sessionService.setUserName(response.data.message.login);
                sessionService.setId(response.data.message.id);
                countCarritoService.updateCarrito();
            }
            deferred.resolve();
        } else {
            sessionService.setSessionInactive();
            sessionService.setAdminInactive();
            sessionService.setClientInactive();
            deferred.resolve();
        }
    }, function () {
        sessionService.setSessionInactive();
        sessionService.setAdminInactive();
        sessionService.setClientInactive();
        $location.path('/home');
    });
    return deferred.promise;
};

barberia66.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/', {templateUrl: 'js/app/common/home.html', controller: 'homeController'});

        $routeProvider.otherwise({redirectTo: '/'});
    }]);