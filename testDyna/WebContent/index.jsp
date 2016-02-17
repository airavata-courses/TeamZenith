<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="SG">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link type="text/css" rel="stylesheet" href="css/bootstrap-theme.min.css">
<link type="text/css" rel="stylesheet" href="css/bootstrap.min.css">
<script type="text/javascript" src = "jquery.js"></script>
<script type="text/javascript" src = "bootstrap.min.js"></script>

</head>
<body>
<script type="text/javascript" src = "lib/angular.min.js"></script>
<script type="text/javascript" src = "app.js"></script>
<div class="intro-message">
<h1>Science Gateway</h1>
<h3>A job scheduler for Karst computer structure by Team
Zenith</h3>
<div>
<section>
<ul class = "nav nav-pills">
<li ng-class ="{active:tab ===1}"> <a href ng-click="tab =1">Submit job</a></li>
<li ng-class ="{active:tab ===2}"><a href ng-click="tab =2">Monitor Job</a></li>
<li ng-class ="{active:tab ===3}">	<a href ng-click="tab =3">CanceJob</a></li>
</ul>
</section>
</div>
<div class = "panel" ng-show="tab ===1">
<h3>Submit Job Form</h3>
<form method="POST" enctype="multipart/form-data" action="http://localhost:8080/KarstShell/upload">
										<h3 class="section-heading">User Details:</h3>
										<fieldset class="form-group">
											<label for="UserID">User Name</label> <input type="username"
												class="form-control" id="exampleInputEmail1"
												placeholder="Enter SSH User ID" name="user"> <small
												class="text-muted">We'll never share your email with
												anyone else.</small>
										</fieldset>
										<fieldset class="form-group">
											<label for="exampleInputEmail1">Email</label> <input
												type="email" class="form-control" id="exampleInputEmail1"
												placeholder="Email id to be notified upon completion" name="emailId">
											<small class="text-muted">We'll never share your
												email with anyone else.</small>
										</fieldset>
										<fieldset class="form-group">
											<label for="exampleInputPassword1">PassPhrase :</label> <input
												type="password" class="form-control"
												id="exampleInputPassword1"
												placeholder="Private Key PassPhrase" name="pass">
										</fieldset>
										<fieldset class="form-group">
											<label for="PrivateKeyFileInput">Private Key File</label> <input
												type="file" class="form-control-file"
												id="PrivateKeyFileInput" name="ppk"> <small class="text-muted">Select
												the pre-configured private key file to be used for SSH
												authentication.</small>
										</fieldset>
										<div role="separator" class="divider"></div>
										<h3 class="section-heading">Job Details:</h3>
										<fieldset class="form-group">
											<label for="JobSourceCode">Input File</label> <input
												type="file" class="form-control-file"
												id="PrivateKeyFileInput" name="file"> <small class="text-muted">Select
												the source code file, compatible types ".c"</small>
										</fieldset>
										<fieldset class="form-group">
											<label for="exampleSelect1">Compilation required ?</label>
											<div class="radio">
												<label> <input type="radio" name="optionsRadios"
													id="optionsRadios1" value="yes" name="isCompile" checked>yes</label>
											</div>
											<div class="radio">
												<label> <input type="radio" name="optionsRadios"
													id="optionsRadios2" name="isCompile" value="no">No</label>
											</div>
										</fieldset>
										<fieldset class="form-group">
											<label for="jobName">Job Name</label> <input type="text"
												class="form-control" id="jobName"
												placeholder="Assign a name to job" name="jobName">
										</fieldset>
										<fieldset class="form-group">
											<label for="nodeCount">Node Count</label> <input type="text"
												class="form-control" id="nodeCount"
												placeholder="1,2,3 etc" name="nodes"> <small
												class="text-muted">Enter the required number of computing nodes</small>
										</fieldset>
										<fieldset class="form-group">
											<label for="UserID">Processor per node</label> <input type="text"
												class="form-control" id="UserID"
												placeholder="1,2,3 or 4" name="ppn">					
										</fieldset>
										<fieldset class="form-group">
											<label for="wallTime">Wall Time</label> <input type="text"
												class="form-control" id="wallTime"
												placeholder="HH:MM:SS" name="walltime">					
										</fieldset>
										<fieldset class="form-group">
											<label for="nodeCount">Remote Workspace path</label> <input type="text"
												class="form-control" id="nodeCount"
												placeholder="/N/u/anujbhan/Karst/workspace" name="tpath"> <small
												class="text-muted">We need a workspace directory on remote machine, Please provide a Absolute path for the same.</small>
										</fieldset>
										<button type="submit" class="btn btn-primary">Submit</button>
										
									</form>
</div>
<div class = "panel" ng-show="tab ===2">
<h3>Monitor Job Form</h3>
<form name="reviewform" method="POST" enctype="multipart/form-data" action="monitor">
<h4>Please enter  user configuration parameters</h3>		
	 Enter the username:<input type="text" name="username"><br /> 
	 Enter the passphrase(if any):<input type="text" name="passPhrase"><br /> 
	 Upload private key :
	<input type="file" name="ppkFile"><br /> 	 	 
	 Enter the job number:<input type="text" name="jobNumber"><br /> 
	<input type="submit"
			value="Fetch job status"> 
	</form>
</div>
<div class = "panel" ng-show="tab ===3">
<h3>Cancel Job Form</h3>
<form method="POST" enctype="multipart/form-data"
		action="cancel">
<h3> User configuration parameters</h3>		
	 Enter the username:<input type="text" name="username"><br /> 
	 Enter the passphrase(if any):<input type="text" name="passPhrase"><br /> 
	 Upload private key :
	<input type="file" name="ppkFile"><br /> 	 	 
	 Enter the job number:<input type="text" name="jobNumber"><br /> 
	<input type="submit"
			value="Cancel job"> 
	</form>
</div>
</body>
</html>