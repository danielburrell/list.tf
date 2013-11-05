'use strict';

/* Controllers */

angular.module('myApp.controllers', ['wantedItem', 'wantedNav']).
  controller('MyCtrl1', ['$routeParams','$scope','$http', function($routeParams,$scope,$http) {


    $scope.getLoginStatus = function() {

            $http.get('/loginStatus').success(function(data) {
            $scope.loggedIn = data.loggedIn;
            if (typeof data.steamId != 'undefined') {
              $scope.mySteamId = data.steamId;
            }
            });


          }

    $scope.getLoginStatus();
  //maybe put this inside the widget

  $scope.id = $routeParams.id;
  console.log($routeParams);
  }])
  .controller('MyCtrl2', [function() {

  }]);
