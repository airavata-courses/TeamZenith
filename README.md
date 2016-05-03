# Zenith  [![Build Status](https://travis-ci.org/airavata-courses/TeamZenith.svg?branch=master)](https://travis-ci.org/airavata-courses/TeamZenith)

Team Zenith Repository for Spring 2016 I590 Class

Team Members :

Anuj Bhandar  
  Master's in Computer Science  
  Indiana University, Bloomington  

Atul Mohan  
  Master's in Computer Science  
  Indiana University, Bloomington 

Arpit Aggarwal  
  Master's in Computer Science  
  Indiana University, Bloomington
  
# Milestone details:-->
  Milestone -5 Continuous integration on Amazon Cloud
  
  * Milestone-4 source code is in currently released for deployment on Amazon Cloud
  * The programmed workflow illustrating the the technology stack is as follows:
	Github --> Travis CI --> Amazon S3 --> Amazon CodeDeploy --> Amazon EC2
  * Github --> Used the Service hook to integrate the repository to Travis CI
  * Travis CI --> Packages the project into a Zip, places the zip in designated S3 bucket and triggers the handle for Amazon CodeDeploy, related files : .travis.yml
  * Amazon S3 --> Used as an intermediate storage area for pre deployed source code package
  * Amazon CodeDeploy --> An amazon cloud service for deploying the application on EC2 instance and installing the same, realted file : appspec.yml, GatewayApp/install.sh (post deployment configuration)
  * Amazon EC2 --> Used the Amazon linux EC2 instance for application server
  * The Source code dependencies are preconfigured on EC2 instances, Amazon RDS as independent database server for metadeta management and inbuilt tomcat 8 as the application server
  * The application can be found on https://ec2-52-24-166-77.us-west-2.compute.amazonaws.com:8443/
  * A self signed application is used for server authentication in TLS protocol, CA signed certificate not in scope of implementation
  * 



