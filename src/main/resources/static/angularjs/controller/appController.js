
angular
	.module('mainApp')
	.controller('appController', function($scope, appFactory){
		$scope.hello = "hello world";
		$scope.lists = appFactory.getData();
		$scope.listFromJson = appFactory.gettestData();
		
		$scope.sayHello = function(){
			console.log("hello");
		}

	});