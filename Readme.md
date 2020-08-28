# CodeTestWithoutSpring 

#### JAX-RS API (run from tomcat installation by building & deploying war)  
You will require Java 8, GIT Client, Maven & PostMan/CURL installed on your local for building and running the project.  
Database used : H2 (auto update tables for Order)  
### 1.Clone the project as below:  
1. open a command prompt or power shell (windows) or CTRL-SHIFT-T ubuntu to open a terminal.(you will need to be a SUDOER)   
2. `git clone https://github.com/ISingh2015/CodeTestWithoutSpring.git`  
  
### 2.Use MAVEN to clean, test & build the web WAR as below:
  
1. cd into project directory and run MAVEN commands to run the web application.  
2.`mvn clean install` 
    
JAR file is generated in the TARGET folder of the web app root (where the project is cloned as above 1).
### 3.RUN the application
CD into the target folder and run as below  
`java -jar codeTest.0.0.1.jar`
  
If run succeeds web app will be up and running. You can browse Product Service end points on the below URL's:
    
### End Points Product Controller :-	
    
#### 1. http://localhost:8080/CodeTest/b/products/allProducts 
	List all products 
#### 2. http://localhost:8080/CodeTest/b/products/byCompany?comp=Apple&price=7000 (list products from apple excluding price =7000)
	Parameters :- comp & price (optional)
	List Products by company  
#### 3. http://localhost:8080/CodeTest/b/products/byCategory?cat=Mobile&price=50000(list products from Mobile category excluding price =50000)
	Parameters :- cat & price (optional)
	List Products by Category  
#### 4. http://localhost:8080/CaseTest/b/products/getStock?prodCode=1 