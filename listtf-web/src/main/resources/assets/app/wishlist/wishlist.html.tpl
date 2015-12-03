<div navbar></div>
<div class="container" >
<dir-pagination-controls ></dir-pagination-controls>





<div dir-paginate="item in wishlist.resultList | itemsPerPage: 10">
    <div class="panel panel-primary">
    <div class="panel-heading">
    {{defIndexToName(item.wantedDocumentId.defIndex)}} ({{item.wantedDocumentId.defIndex}})
        <div class="dropdown pull-right">
                  <button class="btn btn-default btn-xs dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                    Actions
                    <span class="caret"></span>
                  </button>
                  <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                    <li><a href="#" data-toggle="modal" data-target="#myModal">Add Item</a></li>
                    <li><a href="#">Update</a></li>
                  </ul>
                </div>
    </div>
    <div class="panel-body">
        <div class="media">
            <div class="media-left">
                <a href="#">
                    <img class="media-object img-rounded" width="64" height="64" src="{{defIndexToImg(item.wantedDocumentId.defIndex)}}" >
                </a>
            </div>
			<div class="media-body">
			  <h4 class="media-heading"></h4>
			  
			  <table class="table table-condensed"  ng-show="item.wantedDocumentDetails.length">
			      <thead>
			        <tr>
			          <th class="hidden-xs">LVL</th>
			          <th class="hidden-xs">Q</th>
			          <th class="hidden-xs">Trade</th>
			          <th class="hidden-xs">Craft</th>
			          <th class="hidden-xs">#</th>
			          <th class="hidden-xs">Gift</th>
			          <th class="hidden-xs">Price</th>
			          <th class="visible-xs">Description</th>
			        </tr>
			      </thead>
			      <tbody>
			        <tr ng-repeat="wantedDetail in item.wantedDocumentDetails">
			          <td class="hidden-xs"><a rel="noreferrer" target="_blank" href="http://steamcommunity.com/profiles/{{items.steamId}}">{{items.steamId}}</a></td>
			          <td class="hidden-xs">{{items.level}}</td>
			          <td class="hidden-xs"><span ng-class="items.craftNumberPresent ? 'glyphicon glyphicon-alert text-danger' : 'glyphicon glyphicon-remove text-success' " aria-hidden="true"></span> <span ng-show="items.craftNumberPresent">({{items.craftNumber}})</span></td>
			          <td class="hidden-xs"><span ng-class="items.craftable ? 'glyphicon glyphicon-ok text-success' : 'glyphicon glyphicon-remove text-danger' " aria-hidden="true"></span></td>
			          <td class="hidden-xs"><span ng-class="items.giftWrapped ? 'glyphicon glyphicon-alert text-danger' : 'glyphicon glyphicon-remove text-success' " aria-hidden="true"></span></td>
			          <td class="hidden-xs"><span ng-class="items.tradable ? 'glyphicon glyphicon-ok text-success' : 'glyphicon glyphicon-remove text-danger' " aria-hidden="true"></span></td>
			          <td class="hidden-xs"><span ng-class="getQualityCss(items.quality)">{{getQualityName(items.quality)}}</span></td>
			          <td class="hidden-xs"><span ng-class="items.originalId==items.id ? 'glyphicon glyphicon-ok text-success' : 'glyphicon glyphicon-remove text-danger' " aria-hidden="true"></span></td>
			          <td class="visible-xs">toEnglish(wantedDetail)</td>
			        </tr>
			      </tbody>
			  </table>
              <p ng-hide="item.wantedDocumentDetails.length">No details specified</p>
            </div>
           </div>
        </div>
    </div>
</div>
<dir-pagination-controls></dir-pagination-controls>
  
<div modal></div>
</div>