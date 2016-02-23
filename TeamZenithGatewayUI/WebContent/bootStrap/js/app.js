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
        var xhr = new XMLHttpRequest;
        xhr.open('POST', '/', true);
        xhr.send(formData);

        $.ajax({
            url: "http://localhost:8080/gs-uploading-files-0.1.0/monitor",
            type: "POST",
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function () {
                alert('Form Submitted!');
            },
            error: function(){
                alert("error in ajax form submission");
            }
        });

        return false;
    });
});