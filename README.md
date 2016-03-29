# Zenith
Team Zenith Repository for Spring 2016 I590 Class

## Milestone-4 Build instructions :

1. Pull branch Milestone-4 into local system
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
            spring.datasource.password=luta
     * You need to change only the username and password as the database name here is zenith and default port is 3306.
5. Run the tomcat server and navigate browser to "https://localhost:8443/"

