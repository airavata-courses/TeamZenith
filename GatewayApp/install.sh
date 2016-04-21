#!/bin/bash
mvn clean;
mvn install;
mv ./target/GatewayApp-0.1.0.war /var/lib/tomcat7/webapps/ROOT.war


