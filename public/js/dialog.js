var app = angular.module('wantedDialog', []);

app.directive('wantedDialog', function() {
  return {

    restrict : 'A',
    // scope : true, //maybe this

    scope : {
      pushDetailFunction : '&',
      proposedDetail : '='
    },

    templateUrl : '/assets/templates/dialog.html',
    controller : [ '$scope', '$http', function($scope, $http) {

      $scope.addDetail = function() {
        url = '/api/addDetail/' + $scope.proposedDetail.wantedId;
        $http.post(url, $scope.proposedDetail.details).success(function(data) {
          console.log(data);
          console.log("sending" + data.wantedId + "and" + data.details[0]);
          $scope.pushDetailFunction({
            wantedId : data.wantedId,
            details : data.details[0]
          });

          $('#myModal').modal('hide');
          $scope.proposalState = "saved";
        }).error(function(data, status, headers, config) {
          $scope.proposalState = "failed";
        });
      }
      $scope.proposalState = "incomplete";
      $scope.qualities = [ {
        "id" : "0",
        "name" : "Normal",
        "css" : "normal",
      }, {
        "id" : "1",
        "name" : "Genuine",
        "css" : "genuine",
      }, {
        "id" : "3",
        "name" : "Vintage",
        "css" : "vintage",
      }, {
        "id" : "5",
        "name" : "Unusual",
        "css" : "unusual",
      }, {
        "id" : "6",
        "name" : "Unique",
        "css" : "unique",
      }, {
        "id" : "7",
        "name" : "Community",
        "css" : "community",
      }, {
        "id" : "8",
        "name" : "Valve",
        "css" : "valve",
      }, {
        "id" : "9",
        "name" : "Self-Made",
        "css" : "selfmade",
      }, {
        "id" : "11",
        "name" : "Strange",
        "css" : "strange",
      }, {
        "id" : "13",
        "name" : "Haunted",
        "css" : "haunted",
      }, {
        "id" : "-1",
        "name" : "Any Quality",
        "css" : "default",
      } ];

      $scope.tradable = [ {
        "id" : "0",
        "name" : "Untradable"
      }, {
        "id" : "1",
        "name" : "Tradable"
      }, {
        "id" : "2",
        "name" : "Don't care"
      } ];
      $scope.tradableToString = function(tradable) {
        for ( var i = 0; i < ($scope.tradable).length; i++) {
          if ($scope.tradable[i].id == tradable) {
            return $scope.tradable[i].name;
          }
        }
      };
      $scope.craftable = [ {
        "id" : "0",
        "name" : "Uncraftable"
      }, {
        "id" : "1",
        "name" : "Craftable"
      }, {
        "id" : "2",
        "name" : "Don't care"
      } ];

      $scope.craftableToString = function(craftable) {
        for ( var i = 0; i < ($scope.craftable).length; i++) {
          if ($scope.craftable[i].id == craftable) {
            return $scope.craftable[i].name;
          }
        }
      };

      $scope.giftwrapped = [ {
        "id" : "0",
        "name" : "Not Wrapped"
      }, {
        "id" : "1",
        "name" : "Wrapped"
      }, {
        "id" : "2",
        "name" : "Don't care"
      } ];

      $scope.giftwrapToString = function(giftwrapped) {
        for ( var i = 0; i < ($scope.giftwrapped).length; i++) {
          if ($scope.giftwrapped[i].id == giftwrapped) {
            return $scope.giftwrapped[i].name;
          }
        }
      };

      $scope.craftNumberToString = function(craftNumber) {
        if (craftNumber == -1) {
          return "Any";
        } else if (craftNumber == 0) {
          return "Without CraftNumber";
        } else {
          return "Specific CraftNumber";
        }
        ;
      }

      $scope.levelNumberToString = function(levelNumber) {
        if (levelNumber == 0) {
          return "Any";
        } else {
          return "Specific Level";
        }
        ;
      }

      $scope.obtainedToString = function(obtained) {
        if (obtained == 0) {
          return "Unobtained";
        } else {
          return "Obtained";
        }
        ;
      }

      $scope.acceptScope = function(scope) {
        $scope.proposedDetail = scope.proposedDetail;
        console.log($scope);
      }

      $scope.qualityIdToString = function(inputId) {

        for ( var i = 0; i < ($scope.qualities).length; i++) {
          if ($scope.qualities[i].id == inputId) {
            return $scope.qualities[i].name;
          }
        }

      };

    } ],
    link : function(scope, iElement, iAttrs) {
      // scope.acceptScope(scope);
    },

  }
});
