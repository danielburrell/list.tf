var app = angular.module('wantedNav', []);
app.directive('wantedNav', function() {
  return {
    restrict : 'A',

    scope : {
      mySteamId : '=mySteamId',
      loggedIn : '=loggedIn'
    },

    templateUrl : '/assets/templates/nav.html',

    controller : [ '$scope', '$http', function($scope, $http) {
    } ],

    link : function(scope, iElement, iAttrs) {
    },

  }
});
