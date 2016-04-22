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
  Milestone -4 changes are merged to Master (Use Milestone-4 branch for assignment - 3)

## Milestone-4 Build instructions  [![Build Status](https://travis-ci.org/airavata-courses/TeamZenith.svg?branch=master)](https://travis-ci.org/airavata-courses/TeamZenith)
 :

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
     * Install mysql community server
     * open the Workbench and run the DDL file provided inside the project
     * Start the Database
5. Run the tomcat server and navigate browser to "https://localhost:8443/"
6. Create a new account by navigating to the Create Account link. While account creation, please provide the username as the username which is used to login to Karst/BigRed2. This will be the username without the @iu.edu extension.
7. Login to the application with the newly created username and password
8. Once successfully logged in, the interface will display options to Submit, Monitor, Cancel jobs and Download job files
9. For testing GROMACS, we have provided the GROMACS input files within the gromacs folder inside the project folder.
10. 



