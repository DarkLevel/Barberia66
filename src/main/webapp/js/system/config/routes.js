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

var autenticacionAny = function ($q, $location, $http, sessionService, countCarritoService) {
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

var everyone = function ($q, $location, $http, sessionService, countCarritoService) {
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
        $routeProvider.when('/home', {templateUrl: 'js/app/common/home.html', controller: 'homeController', resolve: {auth: everyone}});

        $routeProvider.otherwise({redirectTo: '/home'});
    }]);