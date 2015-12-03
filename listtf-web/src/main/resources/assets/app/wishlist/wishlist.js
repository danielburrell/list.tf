//define a new module called MyHatF2, then define a controller on that module
angular.module('wishlist', [ 'ngRoute','apiservice', 'ngCookies','navigation','angularUtils.directives.dirPagination','modal' ]).config(function($routeProvider) {
	$routeProvider.when('/id/:wishlistId', {
		templateUrl : '/assets/app/wishlist/wishlist.html.tpl',
		controller : 'WishlistCtrl',
	});
}).controller(
		'WishlistCtrl', [ '$scope', '$window', '$cookies', '$location', '$routeParams', 'listtfapi',
		function($scope, $window, $cookies, $location, $routeParams, listtfapi) {
			
			
			$scope.defIndexToName = function(id) {
				for (i = 0; i < $scope.items.length; i++){
					if ($scope.items[i].defindex == id) {
						return $scope.items[i].item_name;
					}
						
				}
			};
			
			$scope.defIndexToImg = function(id) {
				for (i = 0; i < $scope.items.length; i++){
					if ($scope.items[i].defindex == id) {
						return $scope.items[i].image_url;
					}
						
				}
			};
			
			$scope.showAdd = function(id) {
				
			}
			
			function toEnglish(wantedDetail) {
				//if (wantedDetail.)
				var level="Level 100";
				var quality="genuine";
				var tradable="tradable";
				var craftable="craftable";
				var craftnum="with no craft#";
				var gifted="not gifted";
				var price="3 keys";
				return level+" "+quality+" "+tradable+" "+craftable+" "+craftnum+" "+gifted+" "+"for "+price
			}
			
			var getWishList = function () 
			    {
					return listtfapi
							.getWishlist($routeParams.wishlistId)
							.then(function(data) 
							{
								$scope.wishlist = data.data;
								return;
							});
			    },
			    getItems = function() 
			    {
			    	return listtfapi
			    	.getItems()
			    	.then(function(data) 
	    			{
			    		$scope.items = data.data;
			    		return;
	    			});
			    },
			
			    getUserInfo = function() {
			    	return listtfapi
			    	.getUserInfo()
			    	.then(function(data) 
	    			{
			    		$scope.user = data.data;
			    		return;
	    			});
			    };
			
			    getUserInfo().then(getItems).then(getWishList);
			
			
			
		}]);
