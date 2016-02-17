/**
 * 
 */

  var app = angular.module('SG', []);

  

  app.controller('JobController', function(){
    this.tab = 1;

    this.setTab = function(newValue){
      this.tab = newValue;
    };

    this.isSet = function(tabName){
      return this.tab === tabName;
    };
  });

 
