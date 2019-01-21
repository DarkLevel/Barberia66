/* global moment */

'use strict';

var barberia66 = angular.module('MyApp', [
    'ngRoute',
    'services',
    'ngMaterial',
    'components',
    'commonControllers'
]);

var moduleCommon = angular.module('commonControllers', []);
var moduleService = angular.module('services', []);
var moduleComponent = angular.module('components', []);
