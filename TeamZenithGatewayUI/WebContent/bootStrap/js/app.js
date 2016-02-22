
$('#jobSubmitForm1').submit(function () {
	  
	  // Get some values from elements on the page:
	  var $form = $( this ),
	  userVal = $form.find( "input[name='user']" ).val(),
	  emailIdVal = $form.find( "input[name='emailId']" ).val(),
	  passVal = $form.find( "input[name='pass']" ).val(),
	  ppkVal = $form.find( "input[name='ppk']" ).val(),
	  fileVal = $form.find( "input[name='file']" ).val(),
	  isCompileVal = $form.find( "input[name='isCompile']" ).val(),
	  jobNameVal = $form.find( "input[name='jobName']" ).val(),
	  ppnVal = $form.find( "input[name='ppn']" ).val(),
	  walltimeVal = $form.find( "input[name='walltime']" ).val(),
	  tpathVal = $form.find( "input[name='tpath']" ).val(),
	  url = "http://localhost:8080/gs-uploading-files-0.1.0/upload";
	  
	  $.ajax({
          type: 'POST',
          url: url,
          data: { user: userVal, emailId: emailIdVal, pass: passVal, ppk: ppkVal, file: fileVal, isCompile: isCompileVal, jobName:jobNameVal, ppn: ppnVal, walltime: walltimeVal, tpath: tpathVal },
          error: function()
          {
             alert("Request Failed");
          },
          success: function(response)
          {  
        	  $('#myModal').modal('hide');
    		  alert( "Data Loaded: " + response );
          }
	  });
	  
	  return false;
	
});