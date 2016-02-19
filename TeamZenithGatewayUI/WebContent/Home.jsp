<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Science Gateway - Team Zenith</title>

<!-- Bootstrap Core CSS -->
<link href="bootStrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="bootStrap/css/landing-page.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="bootStrap/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
<link
	href="http://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic,700italic"
	rel="stylesheet" type="text/css">

<script src="bootStrap/js/jquery.js" type="text/javascript"></script>
<script src="bootStrap/js/bootstrap.js" type="text/javascript"></script>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<!-- Navigation -->
	<nav class="navbar navbar-inverse navbar-fixed-top"
		role="navigation">
	<div class="container topnav">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">Science Gateway</a>
		</div>
		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="#about">About</a></li>
				<li><a href="#services">Services</a></li>
				<li><a href="#contact">Contact</a></li>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container --> </nav>


	<!-- Header -->
	<a name="about"></a>
	<div class="intro-header">
		<div class="container">

			<div class="row">
				<div class="col-lg-12">
					<div class="intro-message">
						<h1>Science Gateway</h1>
						<h3>A job scheduler for Karst computer structure by Team
							Zenith</h3>
						<hr class="intro-divider">
						<ul class="list-inline intro-social-buttons">
							<li><a href="#" class="btn btn-default btn-lg"><i
									class="fa fa-twitter fa-fw"></i> <span class="network-name">Twitter</span></a>
							</li>
							<li><a href="https://github.com/airavata-courses/TeamZenith"
								class="btn btn-default btn-lg"><i class="fa fa-github fa-fw"></i>
									<span class="network-name">Github</span></a></li>
							<li><a href="#" class="btn btn-default btn-lg"><i
									class="fa fa-linkedin fa-fw"></i> <span class="network-name">Linkedin</span></a>
							</li>
						</ul>
					</div>
				</div>
			</div>

		</div>
		<!-- /.container -->

	</div>
	<!-- /.intro-header -->

	<!-- Page Content -->

	<a name="services"></a>
	<div class="content-section-a">

		<div class="container">
			<div class="row">
				<div class="col-lg-5 col-sm-6">
					<hr class="section-heading-spacer">
					<div class="clearfix"></div>
					<h2 class="section-heading">
						Job Creation
					</h2>
					<p class="lead">
						This service will allow you to create and submit job on Karst computing cluster
					</p>
					<button type="button" class="btn btn-default btn-lg"
						data-toggle="modal" data-target="#myModal">
						<i class="glyphicon glyphicon-cloud-upload"></i> <span
							class="network-name">Create Job</span>
					</button>
					<div id="myModal" class="modal fade" role="dialog">
						<div class="modal-dialog">

							<!-- Modal content-->
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal">&times;</button>
									<h3 class="modal-title">Job Creation</h3>
								</div>
								<div class="modal-body">
									<form method="POST" enctype="multipart/form-data"
										action="http://localhost:8080/KarstShell/upload">
										<h4 class="section-heading">User Details:</h4>
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
										<a role="separator" class="divider"></a>
										<hr>
										<h4 class="section-heading">Job Details:</h4>
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

								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">Close</button>
								</div>

							</div>

						</div>
					</div>
				</div>
				<div class="col-lg-5 col-lg-offset-2 col-sm-6">
					<img class="img-responsive" src="bootStrap/img/ipad.png" alt="">
				</div>
			</div>

		</div>
		<!-- /.container -->

	</div>
	<!-- /.content-section-a -->

	<div class="content-section-b">

		<div class="container">

			<div class="row">
				<div class="col-lg-5 col-lg-offset-1 col-sm-push-6  col-sm-6">
					<hr class="section-heading-spacer">
					<div class="clearfix"></div>
					<h2 class="section-heading">
						3D Device Mockups<br>by PSDCovers
					</h2>
					<p class="lead">
						Turn your 2D designs into high quality, 3D product shots in
						seconds using free Photoshop actions by <a target="_blank"
							href="http://www.psdcovers.com/">PSDCovers</a>! Visit their
						website to download some of their awesome, free photoshop actions!
					</p>
				</div>
				<div class="col-lg-5 col-sm-pull-6  col-sm-6">
					<img class="img-responsive" src="bootStrap/img/dog.png" alt="">
				</div>
			</div>

		</div>
		<!-- /.container -->

	</div>
	<!-- /.content-section-b -->

	<div class="content-section-a">

		<div class="container">

			<div class="row">
				<div class="col-lg-5 col-sm-6">
					<hr class="section-heading-spacer">
					<div class="clearfix"></div>
					<h2 class="section-heading">
						Google Web Fonts and<br>Font Awesome Icons
					</h2>
					<p class="lead">
						This template features the 'Lato' font, part of the <a
							target="_blank" href="http://www.google.com/fonts">Google Web
							Font library</a>, as well as <a target="_blank"
							href="http://fontawesome.io">icons from Font Awesome</a>.
					</p>
				</div>
				<div class="col-lg-5 col-lg-offset-2 col-sm-6">
					<img class="img-responsive" src="bootStrap/img/phones.png" alt="">
				</div>
			</div>

		</div>
		<!-- /.container -->

	</div>
	<!-- /.content-section-a -->

	<a name="contact"></a>
	<div class="banner">

		<div class="container">

			<div class="row">
				<div class="col-lg-6">
					<h2>Connect to Start Bootstrap:</h2>
				</div>
				<div class="col-lg-6">
					<ul class="list-inline banner-social-buttons">
						<li><a href="https://twitter.com/SBootstrap"
							class="btn btn-default btn-lg"><i class="fa fa-twitter fa-fw"></i>
								<span class="network-name">Twitter</span></a></li>
						<li><a
							href="https://github.com/IronSummitMedia/startbootstrap"
							class="btn btn-default btn-lg"><i class="fa fa-github fa-fw"></i>
								<span class="network-name">Github</span></a></li>
						<li><a href="#" class="btn btn-default btn-lg"><i
								class="fa fa-linkedin fa-fw"></i> <span class="network-name">Linkedin</span></a>
						</li>
					</ul>
				</div>
			</div>

		</div>
		<!-- /.container -->

	</div>
	<!-- /.banner -->

	<!-- Footer -->
	<footer>
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<ul class="list-inline">
					<li><a href="#">Home</a></li>
					<li class="footer-menu-divider">&sdot;</li>
					<li><a href="#about">About</a></li>
					<li class="footer-menu-divider">&sdot;</li>
					<li><a href="#services">Services</a></li>
					<li class="footer-menu-divider">&sdot;</li>
					<li><a href="#contact">Contact</a></li>
				</ul>
				<p class="copyright text-muted small">Copyright &copy; Your
					Company 2014. All Rights Reserved</p>
			</div>
		</div>
	</div>
	</footer>

	<!-- jQuery -->
	<script src="bootStrap/js/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="bootStrap/js/bootstrap.min.js"></script>

</body>
</html>