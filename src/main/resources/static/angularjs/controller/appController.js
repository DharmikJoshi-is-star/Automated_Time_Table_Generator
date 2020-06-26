
angular
	.module('mainApp')
	.controller('appController', function($scope, appFactory, $http){
		$scope.hello = "hello world";
		$scope.lists = appFactory.getData();
		$scope.listFromJson = appFactory.gettestData();
		
		$scope.sayHello = function(){
			console.log("hello");
		}
		
		$scope.greeting = "hey";
		
    	$http({
    	    method : 'GET',
    	    url : 'http://localhost:8086/testComponent'
    	}).then(function successCallback(response) {
    		 $scope.greeting = response.data.name;
    	}, function errorCallback(response) {
    	    console.log(response.statusText);
    	});

	});