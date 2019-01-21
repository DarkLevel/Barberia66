'use strict';

barberia66.config(['$locationProvider', function ($locationProvider) {
        $locationProvider.html5Mode(true);
    }]);
barberia66.config(['$httpProvider', function ($httpProvider) {
        $httpProvider.defaults.withCredentials = true;
    }]);