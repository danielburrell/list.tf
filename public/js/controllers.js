'use strict';

/* Controllers */

angular.module('myApp.controllers', [ 'wantedItem', 'wantedNav' ]).controller('MyCtrl1',
    [ '$routeParams', '$scope', '$http', function($routeParams, $scope, $http) {



      $scope.getLoginStatus = function() {

        $http.get('/loginStatus').success(function(data) {
          $scope.loggedIn = data.loggedIn;
          if (typeof data.steamId != 'undefined') {
            $scope.mySteamId = data.steamId;
          }
        });

      }

      $scope.getnameStatus = function() {

        $http.get('/getName/'+$scope.id).success(function(data) {
          $scope.name = data.name;
        });

      }

      $scope.id = $routeParams.id;
      $scope.getLoginStatus();
      $scope.getnameStatus();
      // maybe put this inside the widget

      console.log($routeParams);
    } ]).controller('MyCtrl2', [ function() {

} ]).controller('LoginCtrl',
    [ '$routeParams', '$scope', '$http', function($routeParams, $scope, $http) {

      $scope.getLoginStatus = function() {

        $http.get('/loginStatus').success(function(data) {
          $scope.loggedIn = data.loggedIn;
          if (typeof data.steamId != 'undefined') {
            $scope.mySteamId = data.steamId;
          }
        });

      }

      $scope.login = function() {
      $http.get('/api/login').success(function(data) {
        if (data.steamUrl != undefined) {
          window.location.href=data.steamUrl;
        } else if (data.steamDownUrl != undefined){

        }


      });
      }


    $scope.getLoginStatus();
    $scope.login();


} ]).controller('AboutCtrl',
    [ '$routeParams', '$scope', '$http', function($routeParams, $scope, $http) {

      $scope.getLoginStatus = function() {

        $http.get('/loginStatus').success(function(data) {
          $scope.loggedIn = data.loggedIn;
          if (typeof data.steamId != 'undefined') {
            $scope.mySteamId = data.steamId;
          }
        });

      }



    $scope.getLoginStatus();


} ]).controller('LogoutCtrl',
    [ '$routeParams', '$scope', '$http', '$location', function($routeParams, $scope, $http, $location) {

      $scope.getLogoutCtrl = function() {

        $http.get('/api/logout').success(function(data) {
          $location.path( "/" );
        });

      }



    $scope.getLogoutCtrl();


} ]);
