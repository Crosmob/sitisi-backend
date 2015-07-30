angular.module('SharedModule', ['ui.bootstrap'])

.factory('myDownloadService', 
['$http',
function($http) {    
    var myDownloadService = {};
    
    //Institutions.
    var institutions = {};
    institutions.data = [];
    myDownloadService.getInstitutions = function() {
    	$http.get('api/getInstitutions')
    	.success(function(data, status, headers) {
    		institutions.data = data;
    		//console.log(data);
    	}).error(function(data, status, headers) {
    		alert("Can not download institutions.");
    	});
        return institutions;
    };
    
    //Meal types.
    var mealTypes = {};
    mealTypes.data = [];
    myDownloadService.getMealTypes = function() {
    	$http.get('api/getMealTypes')
    	.success(function(data, status, headers) {
    		mealTypes.data = data;
    	}).error(function(data, status, headers) {
    		alert("Can not download meal types.");
    	});
        return mealTypes;
    };
    
    //Main dishes.
    var oldMainDishes = {};
    oldMainDishes.data = [];
    myDownloadService.getMainDishes = function() {
    	$http.get('api/getMainDishes')
    	.success(function(data, status, headers) {
    		oldMainDishes.data = data;
    	}).error(function(data, status, headers) {
    		alert("Can not download old main dishes.");
    	});
        return oldMainDishes;
    };
    
    //Side dishes.
    var oldSideDishes = {};
    oldSideDishes.data = [];
    myDownloadService.getSideDishes = function() {
    	$http.get('api/getSideDishes')
    	.success(function(data, status, headers) {
    		oldSideDishes.data = data;
    	}).error(function(data, status, headers) {
    		alert("Can not download old side dishes.");
    	});
        return oldSideDishes;
    };
    
    //Menus.
    var menus = {};
    menus.data = [];
    myDownloadService.getMenus = function(downloadAll) {
    	downloadAll = typeof downloadAll !== 'undefined' ? downloadAll : false;
    	var myUrl = "menus";
    	if(!downloadAll) {
    		var dt = new Date();
    	    var tmpDay = ("0" + dt.getDate()).slice(-2);  //Keep the last 2 characters of the string.
    	    var tmpMonth = ("0" + (dt.getMonth()+1)).slice(-2);
    	    var day1 = dt.getFullYear() + "-" + tmpMonth + "-" + tmpDay;
    	    dt.setDate(dt.getDate() + 1);
    	    var tmpDay = ("0" + dt.getDate()).slice(-2);
    	    var tmpMonth = ("0" + (dt.getMonth()+1)).slice(-2);
    	    var day2 = dt.getFullYear() + "-" + tmpMonth + "-" + tmpDay;
    	    dt.setDate(dt.getDate() + 1);
    	    var tmpDay = ("0" + dt.getDate()).slice(-2);
    	    var tmpMonth = ("0" + (dt.getMonth()+1)).slice(-2);
    	    var day3 = dt.getFullYear() + "-" + tmpMonth + "-" + tmpDay;
    		myUrl = "menus/search/findMenuForThreeDays?day1=" + day1 + "&day2=" + day2 + "&day3=" + day3;
    	}
    	//console.log("CALL: " + myUrl);
    	$http.get(myUrl)
    	.success(function(data, status, headers) {
	    	if (data._embedded != undefined) {
	    		menus.data = data._embedded.menus;
			} else {
				alert("data._embedded == undefined");
			}
		}).error(function(data, status, headers) {
			alert("Can not download menus.");
		});
        return menus;
    };

    return myDownloadService;
}])

.factory('myUploadService', 
['$http',
function($http) {
    var myUploadService = {};
    
    myUploadService.uploadMenus = function(menu) {
    	//In order to send the menus.
        $http.defaults.headers.post["Content-Type"] = "application/json; charset=UTF-8";
    	return $http.post('menus', menu);
    };
    
    return myUploadService;
}])

.factory('myDeleteService', 
['$http',
function($http) {
    var myDeleteService = {};
    
    myDeleteService.deleteMenu = function(menuId) {
    	return $http.delete('menus/' + menuId);
    };
    
    return myDeleteService;
}])
