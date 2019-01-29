/* global barberia66 */

var autenticacionAdminJefe = function ($q, $location, $http, sessionService) {
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

var logged = function ($q, $location, $http, sessionService) {
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

        $routeProvider.when('/operaciones', {templateUrl: 'js/app/common/operaciones.html', controller: 'operacionesController', resolve: {auth: logged}});

        $routeProvider.when('/cita/plist/:rpp?/:page?/:order?', {templateUrl: 'js/app/cita/plist.html', controller: 'citaPlistController', resolve: {auth: logged}});
        
        $routeProvider.when('/estadocita/plist/:rpp?/:page?/:order?', {templateUrl: 'js/app/estadocita/plist.html', controller: 'estadocitaPlistController', resolve: {auth: logged}});
        
        $routeProvider.when('/linea/plist/:rpp?/:page?/:order?', {templateUrl: 'js/app/linea/plist.html', controller: 'lineaPlistController', resolve: {auth: logged}});
        
        $routeProvider.when('/producto/plist/:rpp?/:page?/:order?', {templateUrl: 'js/app/producto/plist.html', controller: 'productoPlistController', resolve: {auth: logged}});
        
        $routeProvider.when('/registro/plist/:rpp?/:page?/:order?', {templateUrl: 'js/app/registro/plist.html', controller: 'registroPlistController', resolve: {auth: logged}});
        
        $routeProvider.when('/tipocita/plist/:rpp?/:page?/:order?', {templateUrl: 'js/app/tipocita/plist.html', controller: 'tipocitaPlistController', resolve: {auth: logged}});
        
        $routeProvider.when('/tipoproducto/plist/:rpp?/:page?/:order?', {templateUrl: 'js/app/tipoproducto/plist.html', controller: 'tipoproductoPlistController', resolve: {auth: logged}});
        
        $routeProvider.when('/tiporegistro/plist/:rpp?/:page?/:order?', {templateUrl: 'js/app/tiporegistro/plist.html', controller: 'tiporegistroPlistController', resolve: {auth: logged}});
        
        $routeProvider.when('/tipousuario/create', {templateUrl: 'js/app/tipousuario/create.html', controller: 'tipousuarioCreateController', resolve: {auth: autenticacionAdminJefe}});
        $routeProvider.when('/tipousuario/plist/:rpp?/:page?/:order?', {templateUrl: 'js/app/tipousuario/plist.html', controller: 'tipousuarioPlistController', resolve: {auth: autenticacionAdminJefe}});
        $routeProvider.when('/tipousuario/remove/:id?', {templateUrl: 'js/app/tipousuario/remove.html', controller: 'tipousuarioRemoveController', resolve: {auth: autenticacionAdminJefe}});
        $routeProvider.when('/tipousuario/update/:id?', {templateUrl: 'js/app/tipousuario/update.html', controller: 'tipousuarioUpdateController', resolve: {auth: autenticacionAdminJefe}});
        $routeProvider.when('/tipousuario/view/:id?', {templateUrl: 'js/app/tipousuario/view.html', controller: 'tipousuarioViewController', resolve: {auth: autenticacionAdminJefe}});
        
        $routeProvider.when('/usuario/plist/:rpp?/:page?/:order?', {templateUrl: 'js/app/usuario/plist.html', controller: 'usuarioPlistController', resolve: {auth: logged}});

        $routeProvider.otherwise({redirectTo: '/home'});
    }]);