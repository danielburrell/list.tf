//define a new module called MyHatF2, then define a controller on that module
angular.module('mylisttf', [ 'home', 'wishlist' ]).controller('MyListTFCtrl', [ '$scope',
		function($scope) {
			
		}]).config(function($locationProvider) {
	$locationProvider.html5Mode(true);
});
