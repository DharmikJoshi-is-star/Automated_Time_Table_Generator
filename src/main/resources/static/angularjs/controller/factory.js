angular
	.module('mainApp')
		.factory('appFactory', function($http){
			
			var appData = [
					{
						'id': 1,
						'name': 'abc',
						'salary': 10000
					},
					{
						'id': 2,
						'name': 'abcdef',
						'salary': 20000
					},
					{
						'id': 3,
						'name': 'xyz',
						'salary': 30000
					},
					{
						'id': 4,
						'name': 'pqr',
						'salary': 40000
					}
				];
			
			var testData = [
				{
					'id': 1,
					'name': 'abc',
					'salary': 10000
				},
				{
					'id': 2,
					'name': 'abcdef',
					'salary': 20000
				},
				{
					'id': 3,
					'name': 'xyz',
					'salary': 30000
				},
				{
					'id': 4,
					'name': 'pqr',
					'salary': 40000
				}
			];
			
			
			function getData(){
				return appData;
			}
			
			function gettestData(){
				return testData;
			}
			
			
			return {
				
				getData: getData,
				gettestData: gettestData
			}
			
			
		});