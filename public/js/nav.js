var app = angular.module('wantedNav', []);
app.directive('wantedNav', function() {
  return {
    restrict: 'A',
  scope: {
      mySteamId: '=mySteamId',
      loggedIn: '=loggedIn'
    },
    template: '<nav class="navbar navbar-default" role="navigation">'+
        '<!-- Brand and toggle get grouped for better mobile display -->'+
    '<div class="navbar-header">'+
      '<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">'+
        '<span class="sr-only">Toggle navigation</span>'+
        '<span class="icon-bar"></span>'+
        '<span class="icon-bar"></span>'+
        '<span class="icon-bar"></span>'+
      '</button>'+
      '<a class="navbar-brand" href="#">wanted.tf</a>'+
    '</div>'+

    '<!-- Collect the nav links, forms, and other content for toggling -->'+
    '<div class="collapse navbar-collapse navbar-ex1-collapse">'+
      '<ul class="nav navbar-nav">'+
        '<li class="active"><a href="#">Home</a></li>'+
      '<li><a ng-show="!loggedin" href="#/id/{{mySteamId}}">Most Wanted</a></li>'+
        '<li><a href="/about">About</a></li>'+
      '</ul>'+

    '<form class="navbar-form navbar-left" role="search">'+
        '<div class="form-group">'+
          '<input type="text" class="form-control" placeholder="Search">'+
        '</div>'+
      '</form>'+

      '<ul class="nav navbar-nav navbar-right">'+
    '    <li><a ng-show="!loggedIn" href="/login"><img alt="Sign In" src="/assets/images/sits_small.png"></a>'+
    '<a ng-show="loggedIn" href="/logout">Log Out</a>'+
      '</li>'+
      '</ul>'+
    '</div><!-- /.navbar-collapse -->'+
  '</nav>',
  controller: ['$scope', '$http', function($scope, $http) {



    }],
  link: function(scope, iElement, iAttrs) {
      // get weather details

    //scope.getLoginStatus();
    },


  }
});
