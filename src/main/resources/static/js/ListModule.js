angular.module('ListModule', ['SharedModule'])

.controller('ListController',
['$scope', 'myDownloadService', 'myDeleteService',
function($scope, myDownloadService, myDeleteService) {
	$scope.showAll = false;
	$scope.downloadMenus = function() {
		$scope.menus = myDownloadService.getMenus($scope.showAll);
	}
	$scope.downloadMenus();	//Call imediatelly to get the menus.
	
	$scope.deleteMenu = function(menu) {
		myDeleteService.deleteMenu(menu.id)
		.success(function(data, status, headers) {
			$scope.downloadMenus();
		}).error(function(data, status, headers) {
			alert("Menu not deleted.");
		});
	}
	
	$scope.institutions = myDownloadService.getInstitutions();
	$scope.institution = "";
	
	
}])
