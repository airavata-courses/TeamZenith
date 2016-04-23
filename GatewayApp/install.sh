#!/bin/bash

mvn -f /home/ec2-user/builds/GatewayApp/pom.xml clean;
mvn -f /home/ec2-user/builds/GatewayApp/pom.xml install;
cp /home/ec2-user/builds/GatewayApp/target/GatewayApp-0.1.0.war /usr/share/tomcat7/webapps/gateway.war
sudo service tomcat7 restart


