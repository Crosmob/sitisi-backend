angular.module('InsertModule', ['SharedModule'])

.controller('InsertController',
['$scope', 'myDownloadService', 'myUploadService',
function($scope, myDownloadService, myUploadService) {
	//Date picker initialization today.
	$scope.datePickerOpened = false;
	$scope.datePickerModel = new Date();
	//This function will be called when the date picker opens.
	$scope.openDatePicker = function($event) {
		$event.preventDefault();
		$event.stopPropagation();
		$scope.datePickerOpened = true;
	};
	
	$scope.institutions = myDownloadService.getInstitutions();
	$scope.institution = "all";
	
	$scope.mealTypes = myDownloadService.getMealTypes();
	$scope.mealType = "";
	
	$scope.mainDishes = "";
	$scope.oldMainDishes = myDownloadService.getMainDishes();
	
	$scope.sideDishes = "";
	$scope.oldSideDishes = myDownloadService.getSideDishes();
	
	$scope.addMenu = function() {
		if($scope.institution=="" || $scope.datePickerModel=="" || $scope.mealType=="" || $scope.mainDishes==""){
			alert("All values must be set.");
		} else{
			var dt = new Date($scope.datePickerModel);
	    	var today= dt.getFullYear() + "-" + (dt.getMonth() + 1) + "-" + dt.getDate() + "T12:00:00";	//Add 12 in order to insert the correct date in openshift.
			var menu = {
				institution: $scope.institution,
				mealDay: today,
				mealType: $scope.mealType,
				mainDishes: $scope.mainDishes,
				sideDishes: $scope.sideDishes
			};
			myUploadService.uploadMenus(menu)
			.success(function(data, status, headers) {
				$scope.mainDishes = "";
				$scope.sideDishes = "";
				if ($scope.mealType.localeCompare("Μεσημεριανό")==0) {
					$scope.mealType = "Βραδινό";
				} else if ($scope.mealType.localeCompare("Βραδινό")==0) {
					$scope.mealType = "Μεσημεριανό";
					var tomorrow = new Date($scope.datePickerModel);
					tomorrow.setDate(tomorrow.getDate() + 1);
					$scope.datePickerModel = tomorrow;
				}
			}).error(function(data, status, headers) {
				alert("Menu not added.");
			});
		}
	};
}])
