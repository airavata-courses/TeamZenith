$(document).ready(function () {
    $("#jobMonitorForm").submit(function (event) {
    	

        //disable the default form submission
        event.preventDefault();
        //grab all form data  
       // var form = document.getElementById('jobMonitorForm');
        var formData = new FormData();
        formData.append('username', $('#muser').val());
        formData.append('passPhrase', $('#mpass').val());
        formData.append('jobNumber', $('#jobID').val());
        formData.append('ppkFile', $('input[name=ppkFile]')[0].files[0]);
        //console.log(formData);
        //alert(formData);
        //var formData = $(this).serialize();
       // var xhr = new XMLHttpRequest;
       // xhr.open('POST', '/', true);
       // xhr.send(formData);
        
        var options = {
        	    "show" : "false"
        	}

        $.ajax({
            url: "http://localhost:8080/gs-uploading-files-0.1.0/monitor",
            type: "POST",
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
            	bootstrap_alert.warning(JSON.parse(data));
            },
            error: function(data){
            	
            //$('jobMonitorFormButton').modal('hide');
           // $('#jobMonitorFormButton').click();		
            $('#responseModal').modal('show');
            	
            	//bootstrap_alert.warning('oops something went wrong !!!');
            }
        });

        return false;
    });
    bootstrap_alert.warning = function(message) {
        $('#alert_placeholder').html('<div class="alert alert-success"><a class="close" data-dismiss="alert">×</a><span>'+message+'</span></div>')
    }
});