$(document).ready(function () {
	$("#jobMonitorForm").submit(function (event) {

		$( "monitorResponse" ).empty();
		//disable the default form submission
		event.preventDefault();
		//grab all form data  
		var formData = new FormData();
		formData.append('username', $('#muser').val());
		formData.append('passPhrase', $('#mpass').val());
		formData.append('size', $('#jobID').val());
		formData.append('file', $('input[name=ppkFile]')[0].files[0]);

		var options = {
				"show" : "false"
		}

		$.ajax({
			url: "http://localhost:8080/KarstShell-REST-Api-0.1.0/monitor",
			type: "POST",
			data: formData,
			async: false,
			cache: false,
			contentType: false,
			processData: false,
			success: function (data,status) {
				if(status == 200){
					var content = JSON.stringify(data);
					$('#monitorResponse').html('<div class="alert alert-success"><a class="close" data-dismiss="alert">&times</a><span><table class="table"><thead><tr><th>Job Name</th><th>Job owner</th><th>Job Status</th><th>NodeCt</th><th>User</th></tr></thead><tbody><tr><td>'+data.jobname+'</td><td>'+data.name+'</td><td>'+data.jobstate+'</td><td>'+data.nodect+'</td><td>'+data.user+'</td></tr></tbody></table></span></div>');
				} else {
					var content = JSON.stringify(data);
					$('#monitorResponse').html('<div class="alert alert-danger"><a class="close" data-dismiss="alert">&times</a><span>'+content+'</span></div>');
				}	
			},
			error: function(data,status){
				if(status == 400){
					var content = "Bad Request : Please verify the form details and retry";
					$('#monitorResponse').html('<div class="alert alert-warning"><a class="close" data-dismiss="alert">&times</a><span>'+content+'</span></div>');
				} else {
					var content = JSON.stringify(data);
					$('#monitorResponse').html('<div class="alert alert-danger"><a class="close" data-dismiss="alert">&times</a><span>'+content+'</span></div>');
				}
				
			}
		});

		return false;
	});

	$("#jobSubmitForm").submit(function (event) {

		$( "result" ).empty();
		//disable the default form submission
		event.preventDefault();

		var formData = new FormData();

		formData.append('tpath', $('#targetPath').val());
		formData.append('file', $('input[name=file]')[0].files[0]);
		formData.append('user', $('#userName').val());
		formData.append('jobName', $('#jobName').val());
		formData.append('nodes', $('#nodeCount').val());
		formData.append('ppn', $('#ppn').val());
		formData.append('walltime', $('#wallTime').val());
		formData.append('isCompile', $("input[name=isCompile]:checked").val());
		formData.append('emailId', $('#InputEmail').val());
		formData.append('ppk', $('input[name=ppk]')[0].files[0]);
		formData.append('pass', $('#passPhrase').val());

		var options = {
				"show" : "false"
		}

		$.ajax({
			url: "http://localhost:8080/KarstShell-REST-Api-0.1.0/upload",
			type: "POST",
			data: formData,
			async: false,
			cache: false,
			contentType: false,
			processData: false,
			success: function (data,status) {
				if(status != 200){
					var content = JSON.stringify(data);
					$('#result').html('<div class="alert alert-danger"><a class="close" data-dismiss="alert">&times</a><span>'+content+'</span></div>');
				} else {
					var content = JSON.stringify(data);
					$('#result').html('<div class="alert alert-success"><a class="close" data-dismiss="alert">&times</a><span>'+content+'</span></div>');
				}
			},
			error: function(data,status){
				if(status == 400){
					var content = "Bad Request : Please verify the form details and retry";
					$('#result').html('<div class="alert alert-warning"><a class="close" data-dismiss="alert">&times</a><span>'+content+'</span></div>');
				} else {
					var content = JSON.stringify(data);
					$('#result').html('<div class="alert alert-danger"><a class="close" data-dismiss="alert">&times</a><span>'+content+'</span></div>');
				}

			}
		});

		return false;
	});

	$("#jobCancelForm").submit(function (event) {

		$( "cancelResponse" ).empty();
		//disable the default form submission
		event.preventDefault();
		//grab all form data  
		var formData = new FormData();
		formData.append('username', $('#canceluser').val());
		formData.append('passPhrase', $('#cancelpass').val());
		formData.append('jobnumber', $('#canceljobID').val());
		formData.append('file', $('input[name=cFile]')[0].files[0]);

		var options = {
				"show" : "false"
		}

		$.ajax({
			url: "http://localhost:8080/KarstShell-REST-Api-0.1.0/cancel",
			type: "POST",
			data: formData,
			async: false,
			cache: false,
			contentType: false,
			processData: false,
			success: function (data) {
				var content = JSON.stringify(data);
				$('#cancelResponse').html('<div class="alert alert-success"><a class="close" data-dismiss="alert">&times</a><span>'+content+'</span></div>');

			},
			error: function(data){		
				var content = JSON.stringify(data);
				$('#cancelResponse').html('<div class="alert alert-warning"><a class="close" data-dismiss="alert">&times</a><span>'+content+'</span></div>');
			}
		});

		return false;
	});

	$("#jobDownloadForm").submit(function (event) {

		$( "downloadResponse" ).empty();
		//disable the default form submission
		event.preventDefault();
		//grab all form data  
		var formData = new FormData();
		formData.append('username', $('#downloaduser').val());
		formData.append('passPhrase', $('#downloadpass').val());
		formData.append('jobName', $('#downloadjobName').val());
		formData.append('workPath', $('#downloadjobID').val());
		formData.append('file', $('input[name=cFile]')[0].files[0]);

		var options = {
				"show" : "false"
		}

		$.ajax({
			url: "http://localhost:8080/KarstShell-REST-Api-0.1.0/download",
			type: "POST",
			data: formData,
			async: false,
			cache: false,
			contentType: false,
			processData: false,
			success: function (data) {
				var content = JSON.stringify(data);
				$('#cancelResponse').html('<div class="alert alert-success"><a class="close" data-dismiss="alert">&times</a><span>'+content+'</span></div>');

			},
			error: function(data){		
				var content = JSON.stringify(data);
				$('#cancelResponse').html('<div class="alert alert-warning"><a class="close" data-dismiss="alert">&times</a><span>'+content+'</span></div>');
			}
		});

		return false;
	});
	bootstrap_alert.warning = function(message) {
		$('#result').html('<div class="alert alert-success"><a class="close" data-dismiss="alert">×</a><span>'+message+'</span></div>')
	}	
});