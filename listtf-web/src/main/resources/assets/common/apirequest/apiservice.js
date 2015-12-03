angular.module('apiservice', [ 'ngResource' ]).service(
		"listtfapi",
		[
				'$http',
				'$q',
				function(http, q) {
					return {
						
						getUserInfo : function() {
							var address = q.defer();
							http.get("/api/whoAmI")
									.then(function(data) {
										address.resolve(data);
									}, function(err) {
										address.reject(err);
									});
							return address.promise;
						},
						getWishlist : function(id) {
							var address = q.defer();
							http.get("/api/list?userId="+id)
									.then(function(data) {
										address.resolve(data);
									}, function(err) {
										address.reject(err);
									});
							return address.promise;
						},
						getQualities : function() {
							var address = q.defer();
							http.get("https://schema.tf/api/getAllQualitiesSimple", { cache: true})
									.then(function(data) {
										address.resolve(data);
									}, function(err) {
										address.reject(err);
									});
							return address.promise;
						},
						getItems : function() {
							var address = q.defer();
							http.get("https://schema.tf/api/getAllItemsCdn", { cache: true})
									.then(function(data) {
										address.resolve(data);
									}, function(err) {
										address.reject(err);
									});
							return address.promise;
						},
						getMapTranslation : function() {
							return {"0":"Untradable", "1":"Tradable"};
						},
						getCraftableTranslation : function() {
							return {"0":"Uncraftable", "1":"Craftable"};
						},
						getGiftableTranslation : function() {
							return {"0":"Uncraftable", "1":"Craftable"};
						}
						

					}
				} ]);
