var app = angular.module('wantedNav', []);
app.directive('wantedNav', function() {
  return {
    restrict : 'A',

    scope : {
      mySteamId : '=mySteamId',
      loggedIn : '=loggedIn',
    },

    templateUrl : '/assets/templates/nav.html',

    controller : [ '$scope', '$http', '$routeParams', '$location', function($scope, $http, $routeParams, $location) {
      //$scope.routeParams = $routeParams;
      $scope.routeParams = $routeParams;
      $scope.locationPath = $location.path();
    } ],

    link : function(scope, iElement, iAttrs) {
    },

  }
});
