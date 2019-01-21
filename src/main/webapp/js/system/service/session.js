/* global moduleService, observerCallbacks */

'use strict';

moduleService.service('sessionService', [function () {
        var isSessionActive = false;
        var isAdmin = false;
        var isBoss = false;
        var isWorker = false;
        var userName;
        var userId;
        var productosCarrito = 0;
        var observerCallbacks = [];
        return {
            getUserName: function () {
                return userName;
            },
            setUserName: function (name) {
                userName = name;
                
                angular.forEach(observerCallbacks, function (callback) {
                    callback();
                });
            },
            getId: function () {
                return userId;
            },
            setId: function (id) {
                userId = id;
                
                angular.forEach(observerCallbacks, function (callback) {
                    callback();
                });
            },
            isAdmin: function () {
                return isAdmin;
            },
            setAdminActive: function () {
                isAdmin = true;
                
                angular.forEach(observerCallbacks, function (callback) {
                    callback();
                });
            },
            setAdminInactive: function () {
                isAdmin = false;
                
                angular.forEach(observerCallbacks, function (callback) {
                    callback();
                });
            },
            isBoss: function () {
                return isBoss;
            },
            setBossActive: function () {
                isBoss = true;
                
                angular.forEach(observerCallbacks, function (callback) {
                    callback();
                });
            },
            setBossInactive: function () {
                isBoss = false;
                
                angular.forEach(observerCallbacks, function (callback) {
                    callback();
                });
            },
            isWorker: function () {
                return isWorker;
            },
            setWorkerActive: function () {
                isWorker = true;
                
                angular.forEach(observerCallbacks, function (callback) {
                    callback();
                });
            },
            setWorkerInactive: function () {
                isWorker = false;
                
                angular.forEach(observerCallbacks, function (callback) {
                    callback();
                });
            },
            isSessionActive: function () {
                return isSessionActive;
            },
            setSessionActive: function () {
                isSessionActive = true;
                
                angular.forEach(observerCallbacks, function (callback) {
                    callback();
                });
            },
            setSessionInactive: function () {
                isSessionActive = false;
                
                angular.forEach(observerCallbacks, function (callback) {
                    callback();
                });
            },
            getCountCarrito: function(){
                return productosCarrito;
            },
            setCountCarrito: function(numeroProductos){
                productosCarrito = numeroProductos;
                
                angular.forEach(observerCallbacks, function (callback) {
                    callback();
                });
            },
            registerObserverCallback: function (callback) {
                observerCallbacks.push(callback);
            }
        };
    }]);