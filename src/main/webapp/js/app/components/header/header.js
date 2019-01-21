/* global moduleComponent */

moduleComponent.component('headerComponent', {
    templateUrl: 'js/app/components/header/header.html',
    bindings: {
        eventlistener: '&'
    },
    controllerAs: 'c',
    controller: js
});

function js(toolService, sessionService) {
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

}