# library management system

# Assignment
Design and create a web application to do the following:
User should be able to do the following in the application:
1. Issue a book
2. Return a book
3. Show List of books
4. Search for a book and show results

Create a maven based Java Application and also a database to handle these user requests.
You can use Sprint Boot or any other JAVA BASED framework and Postgresql for the database.
REST API can also be used.
SQL queries for creating and maintaining the application tasks are mandatory.
Points will be given on :
1. Architecture of the entire application code
2. Project should be working with a proper database
3. SQL queries to be added for database design
4. Database connection and handling of data efficiently

### TechUsed
- Java 17
- Maven 3.x
- springboot 3.3.3
- PostgreSQL

## To Run The Project Follow The steps
1. unzip and open the project on vsCode(preferable)
2. To run this project, you just need to configure the database connection in the `application.properties` file.
    Edit the `application.properties` file to match your database configuration and available server port.
    didn't use Hibernate because SQL was mandatory.
3. Run the  springboot app
4. call http://localhost:8081/swagger-ui/index.html on you browser to access all the apis.