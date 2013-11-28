var app = angular.module('wantedFooter', []);
app.directive('wantedFooter', function() {
  return {
    restrict : 'A',

    templateUrl : '/assets/templates/footer.html',
  /*
   * transclude : true, controller : [ '$scope', '$http', '$routeParams',
   * '$location', function($scope, $http, $routeParams, $location) { } ],
   *
   * link : function(scope, iElement, iAttrs) { },
   */

  }
});
