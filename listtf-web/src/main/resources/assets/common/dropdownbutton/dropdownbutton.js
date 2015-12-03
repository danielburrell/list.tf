/*
 * Define a navigation module. every 'page' in my app has it's own module. each module should register it's navigation info here for display purposes.
 * To do this. Navigation service was created. every module should call 'push' passing a piece of info in. somebody somewhere should call
 * getAll to get all the items
 */
angular.module('modal', [ 'apiservice' ]).directive('modal', function() {
	return {
		restrict : 'A',
		templateUrl : '/assets/common/modal/modal.html.tpl',
		replace : true,
		controller : 'ModalController',
		scope : {
			currentId : '=',
			save : '@',
			schema : '=',
		},
	};
}).controller(
		'ModalController',
		[ '$scope', '$location', 'listtfapi',
				function($scope, $location, listtfapi) {

					$scope.saveClick = function() {
						
					}
				} ]);