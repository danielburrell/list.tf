<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Add Detail:
					{{itemName}}</h4>
			</div>
			<div class="modal-body">



				<form class="form-horizontal">
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">Level</label>
						<div class="col-sm-4">
							<input type="number" class="form-control" id="inputEmail3"
								placeholder="e.g. 100">
						</div>
					
						<label for="inputPassword3" class="col-sm-2 control-label">Price</label>
						<div class="col-sm-4">
							<input type="password" class="form-control" id="inputPassword3"
								placeholder="e.g. 2 refined">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label">Tradable</label>
						<div class="col-sm-4">
							<div class="btn-group btn-group-justified" role="group"
								aria-label="...">
								 <div class="btn-group" role="group">
									<button class="btn btn-default dropdown-toggle" type="button"
										id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true"
										aria-expanded="true">
										Tradable <span class="caret"></span>
									</button>
									<ul class="dropdown-menu" aria-labelledby="dropdownMenu2">
										<li><a href="#">Tradable</a></li>
										<li><a href="#">Untradable</a></li>
										<li><a href="#">Any</a></li>
									</ul>
								</div>
							</div>
						</div>
						<label for="inputPassword3" class="col-sm-2 control-label">Craftable</label>
						<div class="col-sm-4">
							<div class="btn-group btn-group-justified" role="group"
								aria-label="...">
								 <div class="btn-group" role="group">
									<button class="btn btn-default dropdown-toggle" type="button"
										id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true"
										aria-expanded="true">
										Craftable <span class="caret"></span>
									</button>
									<ul class="dropdown-menu" aria-labelledby="dropdownMenu2">
										<li><a href="#">Craftable</a></li>
										<li><a href="#">Uncraftable</a></li>
										<li><a href="#">Any</a></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label">Quality</label>
						<div class="col-sm-4">
							<div class="btn-group btn-group-justified" role="group"
								aria-label="...">
								<div class="btn-group" role="group">
									<button class="btn btn-default dropdown-toggle" type="button"
										id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true"
										aria-expanded="true">
										Quality <span class="caret"></span>
									</button>
									<ul class="dropdown-menu" aria-labelledby="dropdownMenu2">
										<li><a href="#">Unique</a></li>
										<li><a href="#">Genuine</a></li>
										<li><a href="#">Any</a></li>
									</ul>
								</div>

							</div>
						</div>
						<label for="inputPassword3" class="col-sm-2 control-label">Numbered</label>
						<div class="col-sm-4">
							<div class="btn-group btn-group-justified" role="group"
                                aria-label="...">
                                 <div class="btn-group" role="group">
								<button class="btn btn-default dropdown-toggle" type="button"
									id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true"
									aria-expanded="true">
									Numbered <span class="caret"></span>
								</button>
								<ul class="dropdown-menu" aria-labelledby="dropdownMenu2">
									<li><a href="#">Yes</a></li>
									<li><a href="#">No</a></li>
									<li><a href="#">Any</a></li>
								</ul>
							</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label">Gifted</label>
						<div class="col-sm-10">
							<div class="dropdown">
								<button class="btn btn-default dropdown-toggle" type="button"
									id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true"
									aria-expanded="true">
									Gifted <span class="caret"></span>
								</button>
								<ul class="dropdown-menu" aria-labelledby="dropdownMenu2">
									<li><a href="#">Yes</a></li>
									<li><a href="#">No</a></li>
									<li><a href="#">Any</a></li>
								</ul>
							</div>
						</div>
					</div>
				</form>



			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary">Save changes</button>
			</div>
		</div>
	</div>
</div>