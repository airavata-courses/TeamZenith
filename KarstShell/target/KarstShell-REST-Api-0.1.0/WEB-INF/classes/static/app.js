/**
 * 
 */


var helloApp = angular.module("SG", [ 'ngResource' ]);

/**
helloApp.factory('Notes', ['$resource', function($resource) {
	return $resource('/upload', null,
	    {
			save: {
            method: 'POST',
            transformRequest: formDataObject,
            headers: {'Content-Type':undefined, enctype:'multipart/form-data'}
        },});
	}]);
function formDataObject (data) {
	console.log()
    var fd = new FormData();
    angular.forEach(data, function(value, key) {
        fd.append(key, value);
    });
    return fd;
}

**/


//var myApp = angular.module('myApp', []);

helloApp.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);
helloApp.controller('HttpController', ['$scope', '$http', function($scope, $http){

    $scope.uploadFile = function(){
        var file = $scope.myFile;
//        var storedata = this;
        
        $scope.datapost = [];
        var fd = new FormData();
        fd.append('file', file);
//We can send anything in name parameter, 
//it is hard coded to abc as it is irrelavant in this case.
        var uploadUrl = "/monitor?username="+$scope.name+'&passphrase='+$scope.passphrase+'&size='+$scope.size;
        console.log(uploadUrl);
        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
        .success(function(response){
        	console.log(response)
        	$scope.datapost = response;
        	$scope.PostValue = true; 
//        	$scope.datapost = response
        })
        .error(function(){
        });
    }
    
    
    $scope.cancelJob = function(){
        var file = $scope.myFile;
//        var storedata = this;
        
        $scope.datapost = [];
        var fd = new FormData();
        fd.append('file', file);
//We can send anything in name parameter, 
//it is hard coded to abc as it is irrelavant in this case.
        var uploadUrl = "/cancel?username="+$scope.namec+'&passphrase='+$scope.passphrasec+'&jobnumber='+$scope.jobnum;
        console.log(uploadUrl);
        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
        .success(function(response){
        	console.log(response)
        	$scope.datapost = response;
        	$scope.CancelMessage = true;
        	$scope.userFormc.$invalid = false;	
//        	$scope.datapost = response
        })
        .error(function(){
        });
    }
    
    
    $scope.submitjob = function(){

        var file = $scope.myFile;
//        var storedata = this;
        var filejob = $scope.jobFile;
        $scope.datapost = [];
        var fd = new FormData();
        fd.append('file', file);
        fd.append('filejob',filejob);
//We can send anything in name parameter, 
//it is hard coded to abc as it is irrelavant in this case.
        var uploadUrl = "/upload?username="+$scope.names+'&passphrase='+$scope.passphrases+'&email='+$scope.email+'&comreq='+$scope.compreq+'&jobname='+$scope.jobName+'&noofnodes='+$scope.noofnodes+'&noofppn='+$scope.noofppn+'&walltime='+$scope.walltime+'&path='+$scope.path;
        
//        var uploadUrl = "/cancel?username="+$scope.namec+'&passphrase='+$scope.passphrasec+'&jobnumber='+$scope.jobnum;
        console.log(uploadUrl);
        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
        .success(function(response){
        	console.log(response)
        	$scope.datapost = response;
        	$scope.CancelMessage = true;
        	$scope.userFormc.$invalid = false;	
//        	$scope.datapost = response
        })
        .error(function(){
        });
    
    }
    
    
    
    
    

}]);

















/********






helloApp.controller("HttpController", [ '$scope', '$resource',
		function($scope, $resource) {
			$scope.users = [];
			
			$scope.uploadedFile = function(element) {
				 $scope.$apply(function($scope) {
				   $scope.files = element.files[0];
				   console.log($scope.files);
				 });
				}

			
			$scope.saveUser = function(){
				//alert($scope.name);
				//$scope.users.push({ 'name':$scope.namedemo, 'username': $scope.username, 'size':$scope.size, 'email':$scope.email , 'file':$scope.file});		
				// Create a resource class object
				//
				var formdata = new FormData();
				formdata.append('file',$scope.files)
				var User = $resource('/upload');
					//User.h
				// Call action method (save) on the class 
				//
				 console.log($scope.files);
				User.save({name:$scope.namedemo,username:$scope.username,size:$scope.size,'temp' : 'sad',email:$scope.email},formdata, function(response){
					$scope.datapost = response;
					console.log(response);
				});
				
				$scope.firstname='';
				$scope.username='';
				$scope.size='';
				$scope.email='';
			}
			
		} ]);
		



*********/