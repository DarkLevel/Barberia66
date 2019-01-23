/* global moduleComponent */

moduleComponent.component('headerComponent', {
    templateUrl: 'js/app/components/header/header.html',
    bindings: {
        eventlistener: '&'
    },
    controllerAs: 'c',
    controller: js
});

function js(toolService, sessionService, $scope, $http, $location) {
    var self = this;

    self.logged = sessionService.isSessionActive();
    self.isAdmin = sessionService.isAdmin();
    self.isBoss = sessionService.isBoss();
    self.isWorker = sessionService.isWorker();
    self.nameUserLogged = sessionService.getUserName();
    self.idUserLogged = sessionService.getId();
    self.isActive = toolService.isActive;
    self.carrito = sessionService.getCountCarrito();

    sessionService.registerObserverCallback(function () {
        self.logged = sessionService.isSessionActive();
        self.isAdmin = sessionService.isAdmin();
        self.isBoss = sessionService.isBoss();
        self.isWorker = sessionService.isWorker();
        self.nameUserLogged = sessionService.getUserName();
        self.idUserLogged = sessionService.getId();
        self.carrito = sessionService.getCountCarrito();
    });

    $scope.login = function () {
        var login = $scope.username;
        var pass = forge_sha256($scope.pass);
        $http({
            method: 'GET',
            url: 'http://localhost:8081/barberia66/barberia66?ob=usuario&op=login&user=' + login + '&pass=' + pass
        }).then(function (response) {
            $scope.status = response.data.status;
            $scope.message = response.data.message;
            if ($scope.status === 200) {
                $scope.nombreUsuario = response.data.message.username;
                $scope.idUsuario = response.data.message.id;
                $scope.idTipoUsuario = response.data.message.obj_tipousuario.id;
                sessionService.setSessionActive();
                sessionService.setUserName($scope.nombreUsuario);
                sessionService.setId($scope.idUsuario);
                if ($scope.idTipoUsuario === 1) {
                    sessionService.setAdminActive();
                    sessionService.setBossInactive();
                    sessionService.setWorkerInactive();
                }
                if ($scope.idTipoUsuario === 2) {
                    sessionService.setAdminInactive();
                    sessionService.setBossActive();
                    sessionService.setWorkerInactive();
                }
                if ($scope.idTipoUsuario === 3) {
                    sessionService.setAdminInactive();
                    sessionService.setBossInactive();
                    sessionService.setWorkerActive();
                }
                $scope.incorrecto = false;
            }
            if ($scope.status === 401) {
                sessionService.setSessionInactive();
                sessionService.setAdminInactive();
                sessionService.setBossInactive();
                sessionService.setWorkerInactive();
                $scope.incorrecto = true;
            }
        }, function (response) {
            $scope.status = response.status;
            $scope.ajaxData = response.data.message || 'Request failed';
        });
    };
    
    $scope.logout = function () {
        $http({
            method: 'GET',
            url: 'http://localhost:8081/barberia66/barberia66?ob=usuario&op=logout'
        }).then(function (response) {
            if (response.data.status === 200) {
                sessionService.setSessionInactive();
                sessionService.setAdminInactive();
                sessionService.setBossInactive();
                sessionService.setWorkerInactive();
                $scope.username = "";
                $scope.pass = "";
                $scope.form.username.$pristine = true;
                $scope.form.pass.$pristine = true;
                $location.url('home');
            }
        });
    };

}