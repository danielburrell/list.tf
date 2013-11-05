var app = angular.module('wantedItem', ['wantedDialog']);

app
    .directive(
        'wantedItem',
        function() {
          return {
            restrict : 'A',
            scope : {
              steamId : '=steamId',
              mySteamId : '=mySteamId',

            },
            template : '<center><div class="btn-group btn-group-lg"> '
                + '<button type="button" class="btn btn-default">Unobtained</button>'
                + '<button type="button" class="btn btn-default">Unknown</button>'
                + '<button type="button" class="btn btn-default">Obtained</button>'
                + '<button type="button" class="btn btn-default">Unwanted</button>'
                + '</div></center>'
                + '<ul class="pager">'
                + '<li ng-class="{true:\'previous disabled\', false:\'previous\'}[currentPage == 0]"><a href="javascript:;"  ng-click="previous(currentPage)">&larr; Older</a></li>'
                + '<li ng-class="{true:\'next disabled\', false:\'next\'}[currentPage == numberOfPages]"><a href="javascript:;"  ng-click="next(currentPage)">Newer &rarr;</a></li></ul>'
                + '<div ng-repeat="item in items | startFrom:currentPage*pageSize | limitTo:pageSize" class="panel panel-primary">'
                + '<div class="panel-heading"><a class="pull-left" href="#">'
                + '<img class="media-object" height="24" width="24" hspace="5" src="http://media.steampowered.com/apps/440/icons/c_ambassador_opt.5a05f0ae3486dc204d2c2e037cda748d58e6bc2b.png" alt=""/>'
                + '</a> {{item.itemId}} <div class="btn-group btn-group-xs pull-right"><button ng-show="steamId == mySteamId" type="button" class="btn btn-xs btn-default" data-toggle="modal" data-target="#myModal"><span class="glyphicon glyphicon-plus"></span> Add Detail</button></div></div>'
                + '<table class="table table-condensed">'
                + '<thead>'
                + '<tr>'
                + '<th></th>'
                + '<th>Quality</th>'
                + '<th>Level</th>'
                + '<th>Tradable</th>'
                + '<th>Craftable</th>'
                + '<th>Craft</th>'
                + '<th>Giftwrapped</th>'
                + '<th>Price Range</th>'
                + '</tr>'
                + '</thead>'
                + '<tbody>'
                + '<tr ng-repeat="detail in item.details">'
                + '<td>'
                + '<div class="btn-group btn-group-xs">'
                + '<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">'
                + 'Action <span class="caret"></span>'
                + '</button>'
                + '<ul class="dropdown-menu" role="menu">'
                + '<li><a href="#/edit/{{detail.detailId}}/"><span class="glyphicon glyphicon-edit"></span> Edit</a></li>'
                + '<li><a href="#/obtained/{{detail.detailId}}/"><span class="glyphicon glyphicon-ok"></span> Mark as obtained</a></li>'
                + '<li class="divider"></li>'
                + '<li><a href="#/delete/{{detail.detailId}}/"><span class="glyphicon glyphicon-remove"></span> Delete</a></li>'
                + '</ul>'
                + '</div>'
                + '</td>'
                + '<td>{{qualityIdToString(detail.quality)}}</td>'
                + '<td>{{detail.levelNumber}}</td>'
                + '<td>{{tradableToString(detail.isTradable)}}</td>'
                + '<td>{{craftableToString(detail.isCraftable)}}</td>'
                + '<td>{{craftNumberToString(detail.craftNumber)}}</td>'
                + '<td>{{giftwrapToString(detail.isGiftWrapped)}}</td>'
                + '<td>{{detail.price}}</td>'
                + '</tr>'
                +

                '</tbody>'
                + '</table>'
                + '</div><div wanted-dialog></div>',
            controller : [
                '$scope',
                '$http',
                function($scope, $http) {

                  $scope.addDetail = function() {

                    url = '/api/addDetail/'
                        + $scope.proposedDetail.wantedId;
                    $http
                        .post(url,
                            $scope.proposedDetail)
                        .success(function(data) {
                          console.log(data);
                        })
                        .error(
                            function(data, status,
                                headers, config) {
                              // called
                              // asynchronously if
                              // an error occurs
                              // or server returns
                              // response with an
                              // error status.
                              console.log(data);
                              console.log(status);
                              console
                                  .log(headers);
                              console.log(config);
                            });

                  }
                  $scope.proposedDetail = {
                    "wantedId" : 1,
                    "details" : [ {
                      "quality" : 6,
                      "levelNumber" : 100,
                      "isTradable" : false,
                      "isCraftable" : false,
                      "craftNumber" : 0,
                      "isGiftWrapped" : false,
                      "price" : "0",
                      "isObtained" : false,
                      "priority" : 0
                    } ]
                  };
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

                  $scope.craftableToString = function(
                      craftable) {
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

                  $scope.giftwrapToString = function(
                      giftwrapped) {
                    for ( var i = 0; i < ($scope.giftwrapped).length; i++) {
                      if ($scope.giftwrapped[i].id == giftwrapped) {
                        return $scope.giftwrapped[i].name;
                      }
                    }
                  };

                  $scope.craftNumberToString = function(
                      craftNumber) {
                    if (craftNumber == -1) {
                          return "Any";
                        } else if (craftNumber == 0) {
                      return "No Craft Number";
                    } else {
                      return "#" + craftNumber;
                    }
                    ;
                  }

                  $scope.qualityIdToString = function(inputId) {

                    for ( var i = 0; i < ($scope.qualities).length; i++) {
                      if ($scope.qualities[i].id == inputId) {
                        return $scope.qualities[i].name;
                      }
                    }

                  };

                  $scope.currentPage = 0;
                  $scope.pageSize = 10;
                  $scope.numberOfPages = function() {
                    return Math.ceil($scope.items.length
                        / $scope.pageSize);
                  }

                  $scope.previous = function(currentPage) {
                    if (currentPage > 0) {
                      return $scope.currentPage--;
                    } else {
                      return $scope.currentPage;
                    }
                    ;
                  };
                  $scope.next = function(currentPage) {
                    if (currentPage < $scope.numberOfPages()) {
                      return $scope.currentPage++;
                    } else {
                      return $scope.currentPage;
                    }
                    ;
                  };

                  $scope.getWanted = function(steamId) {

                    url = '/api/getWantedList/' + steamId;
                    console.log(url);
                    $http.get(url).success(function(data) {
                      $scope.items = data.item;
                    });

                  }
                } ],
            link : function(scope, iElement, iAttrs) {
              // get weather details

              scope.getWanted(scope.steamId);
            },

          }
        });
app.filter('startFrom', function() {
  return function(input, start) {
    start = +start; // parse to int
    return input.slice(start);
  }
});
