#!/bin/bash

ps aux | grep 'java -jar'| awk '{print $2}' | xargs kill -9
mvn -f /home/ec2-user/builds/GatewayApp/pom.xml clean;
mvn -f /home/ec2-user/builds/GatewayApp/pom.xml install;
chmod a+w /home/ec2-user/builds/GatewayApp/target
sudo -u ec2-user java -jar /home/ec2-user/builds/GatewayApp/target/GatewayApp-0.1.0.war >> /home/ec2-user/GatewayServer.log 2>&1 &
sleep 60



