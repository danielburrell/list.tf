'use strict';


// Declare app level module which depends on filters, and services
angular.module('myApp', [
  'ngRoute',
  'myApp.filters',
  'myApp.services',
  'myApp.directives',
  'myApp.controllers'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/id/:id/', {templateUrl: '/assets/partials/partial1.html', controller: 'MyCtrl1'});
  $routeProvider.when('/', {templateUrl: '/assets/partials/partial2.html', controller: 'MyCtrl1'});
  //$routeProvider.when('/openIDCallback', {templateUrl: '/assets/partials/partial2.html', controller: 'MyCtrl3'});
  $routeProvider.otherwise({redirectTo: '/'});
}]);
