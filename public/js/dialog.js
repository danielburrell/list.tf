var app = angular.module('wantedDialog', []);

app
    .directive(
        'wantedDialog',
        function() {
          return {

            restrict : 'A',
            //scope : true,                  //maybe this
            //scope : {						//NOBODY KNOWS WHAT SCOPE REALLY DOES,
                            //we all just pretend to

             // proposedDetail : '=proposedDetail'
            //},
            template : '<!-- Modal -->'
                + '<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">'
                + '<div class="modal-dialog">'
                + '<div class="modal-content">'
                + '<div class="modal-header">'
                + '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>'
                + '<h4 class="modal-title" id="myModalLabel">Add Detail</h4>'
                + '</div>'
                + '<div class="modal-body">'
                + '<form class="form-horizontal" role="form">'
                + '<div class="form-group">'
                + ' <label for="inputEmail3" class="col-sm-3 control-label">Quality</label>'
                + ' <div class="col-sm-9">'
                + '  <!-- Single button -->'
                + '  <div class="btn-group">'
                + '   <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">'
                + '    Action <span class="caret"></span>'
                + '   </button>'
                + '   <ul class="dropdown-menu" role="menu">'
                + '    <li><a href="javascript:;">Unique</a></li>'
                + '    <li><a href="javascript:;">Another action</a></li>'
                + '    <li><a href="javascript:;">Something else here</a></li>'
                + '    <li class="divider"></li>'
                + '    <li><a href="javascript:;">Any</a></li>'
                + '   </ul>'
                + '  </div>'
                + ' </div>'
                + '</div>'
                + '   <div class="form-group">'
                + '    <label for="inputPassword3" class="col-sm-3 control-label">Level</label>'
                + '    <div class="col-sm-9">'
                + '    <div class="row">'
                + '    <div class="col-lg-6">'
                + '      <div class="input-group">'
                + '        <span class="input-group-addon">'
                + '          <input type="checkbox">'
                + '        </span>'
                + '        <input type="text" class="form-control" placeholder="Level Number" ng-model="proposedDetail.details.levelNumber">'
                + '      </div><!-- /input-group -->'
                + '    </div><!-- /.col-lg-6 -->'
                + '    </div><!-- /.row -->'
                + '     </div>'
                + '   </div>'
                + '    <div class="form-group">'
                + '      <label for="inputPassword3" class="col-sm-3 control-label">Tradable</label>'
                + '     <div class="col-sm-9">'
                + '  <!-- Single button -->'
                + '  <div class="btn-group">'
                + '   <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">'
                + '    Action <span class="caret"></span>'
                + '   </button>'
                + '   <ul class="dropdown-menu" role="menu">'
                + '    <li><a href="javascript:;">Tradable</a></li>'
                + '    <li><a href="javascript:;">Untradable</a></li>'
                + '    <li class="divider"></li>'
                + '    <li><a href="javascript:;">Don\'t Care</a></li>'
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
                + '    Action <span class="caret"></span>'
                + '   </button>'
                + '   <ul class="dropdown-menu" role="menu">'
                + '    <li><a href="javascript:;">Craftable</a></li>'
                + '    <li><a href="javascript:;">Uncraftable</a></li>'
                + '    <li class="divider"></li>'
                + '    <li><a href="javascript:;">Don\'t Care</a></li>'
                + '   </ul>'
                + '  </div>'
                + '      </div>'
                + '   </div>'
                + '   <div class="form-group">'
                + '     <label for="inputPassword3" class="col-sm-3 control-label">Craft</label>'
                + '     <div class="col-sm-9">'

                + '<div class="input-group">'
                + '  <div class="input-group-btn">'
                + '    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">Action <span class="caret"></span></button>'
                + '    <ul class="dropdown-menu">'
                + '      <li><a href="#">Without Craftnumber</a></li>'
                + '      <li><a href="#">Specific Craftnumber</a></li>'
                + '      <li class="divider"></li>'
                + '      <li><a href="#">Don\'t Care</a></li>'
                + '    </ul>'
                + '  </div><!-- /btn-group -->'
                + '  <input type="text" class="form-control">'
                + '</div><!-- /input-group -->'

                + '     </div>'
                + '    </div>'
                + '    <div class="form-group">'
                + '      <label for="inputPassword3" class="col-sm-3 control-label">Gift Wrapped</label>'
                + '     <div class="col-sm-9">'
                + '  <!-- Single button -->'
                + '  <div class="btn-group">'
                + '   <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">'
                + '    Action <span class="caret"></span>'
                + '   </button>'
                + '   <ul class="dropdown-menu" role="menu">'
                + '    <li><a href="javascript:;">Gift Wrapped</a></li>'
                + '    <li><a href="javascript:;">Not Gift Wrapped</a></li>'
                + '    <li class="divider"></li>'
                + '    <li><a href="javascript:;">Don\'t Care</a></li>'
                + '   </ul>'
                + '  </div>'
                + '      </div>'
                + '    </div>'
                + '    <div class="form-group">'
                + '      <label for="inputPassword3" class="col-sm-3 control-label">Price Range</label>'
                + '     <div class="col-sm-9">'
                + '        <input type="text" class="form-control" id="inputPassword3" placeholder="Price Range" ng-model="proposedDetail.price">'
                + '      </div>'
                + '    </div>'
                +

                '   </form>'
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

                } ],
            link : function(scope, iElement, iAttrs) {

            },

          }
        });

