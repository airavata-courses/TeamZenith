#!/bin/bash
mvn clean;
mvn install;
mv ./target/GatewayApp-0.1.0.war /usr/share/tomcat7/webapps/gateway.war
sudo service tomcat7 restart


