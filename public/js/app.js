'use strict';


// Declare app level module which depends on filters, and services
angular.module('myApp', [
  'ngRoute',
  'myApp.filters',
  'myApp.services',
  'myApp.directives',
  'myApp.controllers'
]).
config(['$routeProvider','$locationProvider', function($routeProvider, $locationProvider) {
  $locationProvider.html5Mode(true);
  $routeProvider.when('/id/:id/', {templateUrl: '/assets/partials/item.html', controller: 'MyCtrl1'});
  $routeProvider.when('/', {templateUrl: '/assets/partials/home.html', controller: 'MyCtrl1'});
  $routeProvider.when('/steamDown', {templateUrl: '/assets/partials/steamDown.html', controller: 'MyCtrl1'});
  $routeProvider.when('/login', {templateUrl: '/assets/partials/login.html', controller: 'LoginCtrl'});
  $routeProvider.when('/logout', {templateUrl: '/assets/partials/logout.html', controller: 'LogoutCtrl'});
  $routeProvider.when('/about', {templateUrl: '/assets/partials/about.html', controller: 'AboutCtrl'});
  //$routeProvider.when('/openIDCallback', {templateUrl: '/assets/partials/partial2.html', controller: 'MyCtrl3'});
  $routeProvider.otherwise({redirectTo: '/'});
}]);
