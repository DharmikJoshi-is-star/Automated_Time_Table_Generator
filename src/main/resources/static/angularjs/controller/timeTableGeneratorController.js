/**
 * 
 */
angular
	.module('mainApp')
	.controller('timeTableGeneratorController', function($scope, appFactory, $http){
		
		
		
		
		$scope.timetable = "";
		
    	$http({
    	    method : 'GET',
    	    url : 'http://localhost:8086/getCollege/'+188
    	}).then(function successCallback(response) {
    		 $scope.college = response.data;
    	}, function errorCallback(response) {
    	    console.log(response.statusText);
    	});

    	$scope.generateTable = function(){
    		
    		$http({
        	    method : 'GET',
        	    url : 'http://localhost:8086/generateTimeTable/'+188
        	}).then(function successCallback(response) {
        		 $scope.timetable = response.data;
        	}, function errorCallback(response) {
        	    console.log(response.statusText);
        	});
    		
    		
    	}
    	
    	
    	$scope.getStreams = function(){
    		
    	}
    	
    	$scope.getStandards = function(){
    		
    	}
    	
	});