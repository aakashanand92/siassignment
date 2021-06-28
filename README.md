# siassignment

This is a clipboard app which can be used to share text with someone over the internet via a link.

The clipboard can be private or public depending on how you set it up.



## Code Setup -

This is a maven project, so after cloning the data, you need to import the dependencies

Download maven on your system

To download all dependencies -

```
mvn clean install -U
```
To package and make jar file -

```
mvn package
```
This will create the jar file in target folder


To run the jar file -

```
java -jar target/siassignment-<version>-SNAPSHOT.jar
```

Now you can access the UI at -  
http://localhost:8080/


## DB Setup -

This is project is configured to use MySQL as a database ,  

Default Settings -  
db - siassignment  
url - localhost:3306  
username - root  
password - password


You can always change it, just make sure to update the application.properties with the new values.
