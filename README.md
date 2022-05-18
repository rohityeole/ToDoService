## ToDo Service

Implementation of `ToDo List` backend Service.

One `Item` of ToDo List contains below details
*	Description
*	Status 
*	Due Date
*	Creation Date
*	Completion Date
*	Updated Date

### REST APIs
RESTful APIs of the Service can be accessed with the help of Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

-	[Add Item](http://localhost:8080/item/add) 			&emsp; &emsp; &emsp;  		- http://localhost:8080/item/add
-	[Update Item](http://localhost:8080/item/update) 	&emsp; &emsp;				- http://localhost:8080/item/update
-	[Get Item](http://localhost:8080/item/1) 			&emsp; &emsp;&emsp;			- http://localhost:8080/item/{id}
-	[Get Item with Status 'NOT_DONE'](http://localhost:8080/item/status)	&emsp; &emsp; 	- http://localhost:8080/item/status
-	[Get All Items](http://localhost:8080/item/status?all=true) 	&emsp;&emsp;		- http://localhost:8080/item/status?all=true

### Validations considered 
*	Default status for the Item is considered as NOT_DONE
* 	ENUM is used to get the STATUS values as `"NOT_DONE, DONE, PAST_DUE"`
*	Default Status of `NOT_DONE` is selected if any invalid value received with "Add Item" request
*	If an Item is Created as `DONE` then CompletedAt Date is updated as "Current Date"
*	If an Item is Updated as `DONE` and earlier Status was `NOT_DONE` then CompletedAt Date is updated as "Current Date" 
*	Update Operation of Item with Status `PAST_DUE` throws an exception to restricts users


###  Non-Functional Implementations in Backend Service
-	Spring Boot 	-> 	For faster implementation of requirement and Futuristic approach to scale for Microservices
-	Spring Rest 	->	To enable Restful APIs and expose to be used by Frontend To Do List
-	Spring Batch	->	To schedule auto-update status of Tasks which are Overdue 	
-	H2-Database		-> 	Serves as Data-Layer during implementation
-	Docker
-	ControllerAdvice	-> To handle exceptions Globally thrown at Data / Service Layer by Controller. Aspect Oriented Implementation
-	Integration Tests to Validate the Rest Service APIs using Mockito
-	Swagger UI		-> 	Swagger-UI for User-friendly access to REST APIs

## How to build the Service
-	Application is dockerized and Container Image can be build from the code using 

&emsp; &emsp; &emsp; ``docker-compose up --build``

-	Integation Test can be executed using file `ItemControllerTest.java` located under `src/test/java` in package `com.simple.system.todo.controller`


## Next Steps
-	Further Granular level exception handling using ErrorCode with the help of CustomException for better Understanding with Frontend Developement
-	Pagination with Search Query on the Item to implement Lazy loading on Frontend
<br>