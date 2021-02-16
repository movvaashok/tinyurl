# Tiny Url

# Overview
The aim of this project is to create a tiny url for a given long length url, and navigate back the original url when tiny url is used to search.

# Building Jar
Maven is used as build tool in this application
1) Clone the project into your computer using (https://github.com/movvaashok/tinyurl.git)
2) Navigate to "tinyurl" folder
3) Open command prompt and run the command **mvn clean install** ( Maven has to be installed in system to execute **mvn clean install** command)
   maven will run unit tests and creates a jar for the project in "target" folder
4) tinyurl-0.0.1-SNAPSHOT.jar will be created.
# Executing Jar
1) Open command prompt in the target folder
2) Run the jar file with the command **java -jar tinyurl-0.0.1-SNAPSHOT.jar**
   The application will start and tomact sever will run on port 8082.
# Rest Endpoints
The application will expose the following rest endpoints
1) /createTinyUrl (POST)
2) /getOriginalUrl (GET)
3) /viewOriginalUrl (GET)
4) /save (GET)

# Creating Tiny url
1) Rest Client (POSTMAN) is required to invoke **/createTinyUrl** ,
The API accepts json data and the payload format is { "originalUrl":"urlLink" }
 
 example:  {"originalUrl":"https://www.google.com/search?q=neueda+careers&rlz=1C1CHBF_enIN810IN810&oq=neueda&aqs=chrome.1.69i57j0l4j69i60l3.3081j0j7&sourceid=chrome&ie=UTF-8"}

The url is converted to tiny url and sent back as JSON response __ 
{ "tinyUrl":"www.neueda.com/d98cde" }

# Fetching & Redirecting Original URL
1) The original url can be fetched by using rest client with **/getOriginalUrl** endpoint
example: http://localhost:8082/tinyurl/getOriginalUrl?tiny=www.neueda.com/d98cde
The rest api will provide original url as json response
example: { "OriginalURL": "https://www.google.com/search?q=neueda+careers&rlz=1C1CHBF_enIN810IN810&oq=neueda&aqs=chrome.1.69i57j0l4j69i60l3.3081j0j7&sourceid=chrome&ie=UTF-8" }
2) Using tiny url in web browser can navigate to original url with restend point **/viewOriginalUrl**
usage: http://localhost:8082/tinyurl/viewOriginalUrl?tiny=www.neueda.com/d98cde

# Save Url Mappings
when url mapping is created they are stored in mappings.json file by default, 
However using **/save** endpoint we can save the mappings using rest client.

# Test Coverage.
Jacoco library is used to test test coverage, and the plugin is configured in pom.xml. When unit tests are excuted jacoco creates a coverage report in the target folder 
**/target/TestCoverageReports/index.html**
