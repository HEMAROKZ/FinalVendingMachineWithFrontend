# VENDING MACHINE SPRING BOOT APPLICATION .

* Developed an application to simulate an functanility of an vending machine where user will be able to purchase the product and admin with authentication crediantials      will be able to do CRUD operation .
* this application has two process Admin process and customer process,in admin process endpoints,  admin functionality has been inculded which is protected by authentication and customer process endpoints is  open to access as it contains purchase product logic .
* Inventory product details  and purchase histoy has been stored in MS-SQL Database.
* this application has two different controllers ,one controller group return responsebody as an json and other use modelandviev to render frontend using JSP pages .
* Used both Spring security and JWT token authentication .
* used Logger to log the required info for debugging the application code .
* Added an Billing Counter using session where  three counter will be active for user ,when ever user is allocated one counter it will not be avialiable for other user .
* this application acts as an server application form which other application consume this rest endpoints using Resttemplate .

## DENOMINATION 

* Added denomination to simulate user try to purchase the product with several different denominations .
* Current Denominations are 50,20,10,5,2,1.
* Denomintion count has been stored in the database ,count of the denomination used will be updated after every sucessful purchase of the product.

DATABASE CONNECTIVITY

* Used MS-SQL for database and spring JDBC for connectivity and added custom query in DAO to fetch and manupliate data from database using namedParameterJdbcTemplate .
* Also used Flyway as a database migration tool.
* Tables in database present are  product table,pruchase history table,denomination table and order line table .

SECURITY

* For the Rest controller ,the endpoints has been protected by JWT token,where token has to be passed in header to be authenticated to access the endpoints .
* for the controller with modelandview resolver admin process end points has to be authenticated with crediantials in an  custom login page to be access and perform CRUD operations.

BILL GENERATION

* added bill generation which will generate a bill after every sucessfull trancation in the below format and also added download button in front end to download the below details in txt file. 

example output{ 
Date and Time: 2023-12-28 18:00:23

Total Change: 20.0 Rupees

Denominations:
20 Rupees: 1 notes/coins

Details for Each Product:
Product Name: lays
Product Price: 10 Rupees
Number Of Quantity Purchased: 1

Product Name: cheetos
Product Price: 20 Rupees
Number Of Quantity Purchased: 1
}




