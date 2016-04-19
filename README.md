# Zenith
Team Zenith Repository for Spring 2016 I590 Class

## Milestone-6 Build instructions :

### Architecture

1. OAuth2 is configured to secure the middleware API microservice and below is brief architecture of the same
   ![alt tag](https://github.com/airavata-courses/TeamZenith/blob/Milestone-6/ReadMe%20content/oauth.png)
2. The database used is MySQL, which could easily be swapped with other database venders by modifying below mentioned part in applications.properties file

   ```sh
    spring.jpa.database-platform: org.hibernate.dialect.MySQL5Dialect 
    spring.jpa.show-sql: true 
    spring.jpa.hibernate.ddl-auto: update 
    entitymanager.packagesToScan: org.airavata.teamzenith
   ```
3. The database script is included in resource folder, which should be run before deploying the microservice.
4. Below are the examples to access the token and interact with the API

   * Access secure resource with token
```sh
curl -i -k https://localhost:8443/api/secure

{"timestamp":1444985908768,"status":401,"error":"Unauthorized","message":"Access Denied","path":"/api/secure"}
```

   * Fetching refresh_token
```sh
curl -vu rajithapp:secret -k 'https://localhost:8443/api/oauth/token?username=admin&password=admin&grant_type=password'

{"access_token":"91202244-431f-444a-b053-7f50716f2012","token_type":"bearer","refresh_token":"e6f8624f-213d-4343-a971-980e83f734be","expires_in":1738,"scope":"read write"}
```

   * Fetching acess_token by submitting refresh_token
```sh
curl -vu rajithapp:secret -k 'https://localhost:8443/api/oauth/token?grant_type=refresh_token&refresh_token=<refresh_token>'

{"access_token":"821c99d4-2c9f-4990-b68d-18eacaff54b2","token_type":"bearer","refresh_token":"e6f8624f-213d-4343-a971-980e83f734be","expires_in":1799,"scope":"read write"}
```

   * Access secure resource sucessfully
```sh
curl -i -H "Authorization: Bearer <access_token>" -k https://localhost:8443/api/secure

Secure Hello!
```

References used "https://github.com/rajithd/spring-boot-oauth2"


## Build instructions :

1. Pull branch Milestone-6 into local system
2. Build the project using Maven build tool, Either use terminal and type "mvn build" or use eclipse and choose "Maven Install"
3. Server Configuration
    * Create Keystore and Self-signed Certificate
        C:\jdk\bin>keytool -genkey -alias srccodes -keyalg RSA -keystore c:\tomcat7\conf\srccodes.jks
        
           * -keystore Filepath (say "c:\tomcat7\conf\srccodes.jks") where keystore file will be generated.

           * keystore password Password of the keystore to be used by Tomcat. If not provided, then default is "changeit".

           * key password Password of the self-signed certificate generated in the keystore. If not provided, then it'll be same as keystore password.
    * Tomcat Configuration
    
           * Open <tomcat-installation-directory>/conf/server.xml in a text editor.
           * Replace the existing connector with the following
           
           ```<Connector port="8443" protocol="HTTP/1.1" SSLEnabled="true"
           maxThreads="150" scheme="https" secure="true"
           clientAuth="false" sslProtocol="TLS"
           keystoreFile="conf/srccodes.jks"
           keystoreType="JKS"
           keystorePass="pass4keystore"
           keyPass="pass4key" /> ```
           * Provide keystoreFile, keystorePass and keyPass values as given in Step #1.
    *  To run the application on Server's root context, remove existing root folder from "webApps" folder and rename the generated application War file to "ROOT.war"


4. Database Configuration
     * Install mysql community server using the following command:
            sudo apt-get install mysql-server
     * Please make a note of the password.Default username is root.
     * Test the connection by logging into mysql using the command:
            mysql -u root -p
     * Open the Workbench and run the DDL.txt text file provided inside the project folder
     * Modify the application.properties present in GatewayApp/src/main/resources by editing the following line with your DB information:
            spring.datasource.url=jdbc:mysql://localhost:3306/zenith
            spring.datasource.username=root
            spring.datasource.password=demo
     * You need to change only the username and password as the database name here is zenith and default port is 3306.
