var app = angular.module('wantedDialog', []);

app
    .directive(
        'wantedDialog',
        function() {
          return {

            restrict : 'A',
            // scope : true, //maybe this

            scope : {
              pushDetailFunction : '&',
              proposedDetail : '='
            },

            template : '<!-- Modal -->'
                + '<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">'
                + '<div class="modal-dialog">'
                + '<div class="modal-content">'
                + '<div class="modal-header">'
                + '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>'
                + '<h4 class="modal-title" id="myModalLabel">Add Detail {{proposedDetail.wantedId}}</h4>'
                + '</div>'
                + '<div class="modal-body">'
                + '<form class="form-horizontal" role="form">'
                + '<div class="form-group">'
                + ' <label for="inputEmail3" class="col-sm-3 control-label">Quality</label>'
                + ' <div class="col-sm-9">'
                + '  <!-- Single button -->'
                + '  <div class="btn-group">'
                + '   <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">'
                + '    {{qualityIdToString(proposedDetail.details.quality)}} <span class="caret"></span>'
                + '   </button>'
                + '   <ul class="dropdown-menu" role="menu">'
                + '    <li ng-repeat="quality in qualities"><a href="javascript:;" ng-click="proposedDetail.details.quality=quality.id">{{quality.name}}</a></li>'
                + '   </ul>'
                + '  </div>'
                + ' </div>'
                + '</div>'
                + '   <div class="form-group">'
                + '    <label for="inputPassword3" class="col-sm-3 control-label">Level</label>'
                + '    <div class="col-sm-9">'
                + '<div class="input-group">'
                + '  <div class="input-group-btn">'
                + '    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">{{levelNumberToString(proposedDetail.details.levelNumber)}} <span class="caret"></span></button>'
                + '    <ul class="dropdown-menu">'
                + '      <li><a href="javascript:;" ng-click="proposedDetail.details.levelNumber=0">Any</a></li>'
                + '      <li><a href="javascript:;" ng-click="proposedDetail.details.levelNumber=undefined">Specific Level</a></li>'
                + '    </ul>'
                + '  </div><!-- /btn-group -->'
                + '  <input type="text" class="form-control" placeholder="Level Number #" ng-hide="proposedDetail.details.levelNumber == 0" ng-model="proposedDetail.details.levelNumber">'
                + '</div><!-- /input-group -->'

                + '     </div>'
                + '   </div>'
                + '    <div class="form-group">'
                + '      <label for="inputPassword3" class="col-sm-3 control-label">Tradable</label>'
                + '     <div class="col-sm-9">'
                + '  <!-- Single button -->'
                + '  <div class="btn-group">'
                + '   <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">'
                + '    {{tradableToString(proposedDetail.details.isTradable)}} <span class="caret"></span>'
                + '   </button>'
                + '   <ul class="dropdown-menu" role="menu">'
                + '    <li><a href="javascript:;" ng-click="proposedDetail.details.isTradable=1">Tradable</a></li>'
                + '    <li><a href="javascript:;" ng-click="proposedDetail.details.isTradable=0">Untradable</a></li>'
                + '    <li class="divider"></li>'
                + '    <li><a href="javascript:;" ng-click="proposedDetail.details.isTradable=2">Don\'t Care</a></li>'
                + '   </ul>'
                + '  </div>'
                + '     </div>'
                + '    </div>'
                + '  <div class="form-group">'
                + '     <label for="inputPassword3" class="col-sm-3 control-label">Craftable</label>'
                + '     <div class="col-sm-9">'
                + '  <!-- Single button -->'
                + '  <div class="btn-group">'
                + '   <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">'
                + '    {{craftableToString(proposedDetail.details.isCraftable)}} <span class="caret"></span>'
                + '   </button>'
                + '   <ul class="dropdown-menu" role="menu">'
                + '    <li><a href="javascript:;" ng-click="proposedDetail.details.isCraftable=1">Craftable</a></li>'
                + '    <li><a href="javascript:;" ng-click="proposedDetail.details.isCraftable=0">Uncraftable</a></li>'
                + '    <li class="divider"></li>'
                + '    <li><a href="javascript:;" ng-click="proposedDetail.details.isCraftable=2">Don\'t Care</a></li>'
                + '   </ul>'
                + '  </div>'
                + '</div>'
                + '   </div>'
                + '   <div class="form-group">'
                + '     <label for="inputPassword3" class="col-sm-3 control-label">Craft Number</label>'
                + '     <div class="col-sm-9">'

                + '<div class="input-group">'
                + '  <div class="input-group-btn">'
                + '    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">{{craftNumberToString(proposedDetail.details.craftNumber)}} <span class="caret"></span></button>'
                + '    <ul class="dropdown-menu">'
                + '      <li><a href="javascript:;" ng-click="proposedDetail.details.craftNumber=0">Without Craftnumber</a></li>'
                + '      <li><a href="javascript:;" ng-click="proposedDetail.details.craftNumber=undefined">Specific Craftnumber</a></li>'
                + '      <li class="divider"></li>'
                + '      <li><a href="javascript:;" ng-click="proposedDetail.details.craftNumber=-1">Don\'t Care</a></li>'
                + '    </ul>'
                + '  </div><!-- /btn-group -->'
                + '  <input type="text" class="form-control" placeholder="Craft Number #" ng-hide="proposedDetail.details.craftNumber == 0 || proposedDetail.details.craftNumber == -1" ng-model="proposedDetail.details.craftNumber">'
                + '</div><!-- /input-group -->'

                + '     </div>'
                + '    </div>'
                + '    <div class="form-group">'
                + '      <label for="inputPassword3" class="col-sm-3 control-label">Gift Wrapped</label>'
                + '     <div class="col-sm-9">'
                + '  <!-- Single button -->'
                + '  <div class="btn-group">'
                + '   <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">'
                + '    {{giftwrapToString(proposedDetail.details.isGiftWrapped)}} <span class="caret"></span>'
                + '   </button>'
                + '   <ul class="dropdown-menu" role="menu">'
                + '    <li><a href="javascript:;" ng-click="proposedDetail.details.isGiftWrapped=1">Gift Wrapped</a></li>'
                + '    <li><a href="javascript:;" ng-click="proposedDetail.details.isGiftWrapped=0">Not Gift Wrapped</a></li>'
                + '    <li class="divider"></li>'
                + '    <li><a href="javascript:;" ng-click="proposedDetail.details.isGiftWrapped=2">Don\'t Care</a></li>'
                + '   </ul>'
                + '  </div>'
                + '      </div>'
                + '    </div>'
                + '    <div class="form-group">'
                + '      <label for="inputPassword3" class="col-sm-3 control-label">Price Range</label>'
                + '     <div class="col-sm-9">'
                + '        <input type="text" class="form-control" id="inputPassword3" placeholder="Price Range" ng-model="proposedDetail.details.price">'
                + '      </div>'
                + '    </div>'
                + '<div class="form-group">'
                + '     <label for="inputPassword3" class="col-sm-3 control-label">Obtained</label>'
                + '     <div class="col-sm-9">'
                + '  <!-- Single button -->'
                + '  <div class="btn-group">'
                + '   <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">'
                + '    {{obtainedToString(proposedDetail.details.isObtained)}} <span class="caret"></span>'
                + '   </button>'
                + '   <ul class="dropdown-menu" role="menu">'
                + '    <li><a href="javascript:;" ng-click="proposedDetail.details.isObtained=0">Unobtained</a></li>'
                + '    <li><a href="javascript:;" ng-click="proposedDetail.details.isObtained=1">Obtained</a></li>'
                + '   </ul>'
                + '  </div>'
                + '</div>'
                + '   </form>'
                + '  </div>'
                + '   <div class="modal-footer">'
                + '   <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>'
                + '   <button type="button" class="btn btn-primary" ng-click="addDetail()" >Save changes</button>'
                + '  </div>'
                + '  </div><!-- /.modal-content -->'
                + ' </div><!-- /.modal-dialog -->'
                + ' </div><!-- /.modal -->',
            controller : [
                '$scope',
                '$http',
                function($scope, $http) {

                  $scope.addDetail = function() {
                    url = '/api/addDetail/'
                        + $scope.proposedDetail.wantedId;
                    $http
                        .post(url,
                            $scope.proposedDetail.details)
                        .success(function(data) {
                          console.log(data);
                          console.log("sending"+ data.wantedId+"and"+data.details[0]);
                          $scope.pushDetailFunction(data.wantedId,data.details[0]);

                          $scope.proposalState = "saved";
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
                      return "Without CraftNumber";
                    } else {
                      return "Specific CraftNumber";
                    }
                    ;
                  }

                  $scope.levelNumberToString = function(
                      levelNumber) {
                    if (levelNumber == 0) {
                      return "Any";
                    } else {
                      return "Specific Level";
                    }
                    ;
                  }

                  $scope.obtainedToString = function(
                          obtained) {
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
