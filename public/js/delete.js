var app = angular.module('wantedDelete', []);

app.directive('wantedDelete', function() {
	return {

		restrict : 'A',

		scope : {
			removeDetailFunction : '&',
			proposedDetailDelete : '=',
			proposedWantedDelete : '='
		},

		templateUrl : '/assets/templates/delete.html',
		controller : [
				'$scope',
				'$http',
				function($scope, $http) {

					$scope.deleteDetail = function() {
						detailId = $scope.proposedDetailDelete;
						wantedId = $scope.proposedWantedDelete;
						url = '/api/deleteDetail/'
								+ $scope.proposedWantedDelete + '/'
								+ $scope.proposedDetailDelete;
						$http.post(url).success(function(data) {
							console.log(data);
							$scope.removeDetailFunction({
								wantedId : wantedId,
								detailId : detailId
							});
							$('#delete').modal('hide');
						}).error(function(data, status, headers, config) {
							$scope.proposalState = "failed";
						});
					}

					$scope.proposalState = "incomplete";

					$scope.acceptScope = function(scope) {
						$scope.proposedDetail = scope.proposedDetail;
						console.log($scope);
					}

				} ],
		link : function(scope, iElement, iAttrs) {
		},

	}
});
