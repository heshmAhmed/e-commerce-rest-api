
# E-commerce SOAP and REST API

An E-Commerce RESTful and SOAP web service built using JAX-RS and JAX-WS.


## 📃 Documentation

[Postman RESTful API](https://documenter.getpostman.com/view/7099221/UyxdKUR6)

[SOAP-UI project](https://github.com/heshmAhmed/e-commerce-rest-soap-api/blob/main/e-commerce-api-soapui-project.xml)



## 📦 Features
- Pagination
- Partial response
- Filtering



## ⚙ Technologies used
- JAX-RS (Jersey)
- JAX-WS (Metro)
- JSON-B
- JAX-B
- JAX-P
- Maven
- Map struct
- Lomboke
- Tomcat
- Jakarta persistance (Hibernate)
- MySql
- Intellij IDEA Ultimate
- Postman
- SOAP-UI
## 🛠 Run with Maven
Clone the project

```bash
  git clone https://github.com/heshmAhmed/e-commerce-rest-soap-api
```

Go to the project directory

```bash
  cd e-commerce-rest-soap-api
```
- Create db user and set the username and password values in the persistence.xml
- Create db named e-commerce in your MySql Server 
- Run your tomcat apache server and then change the configuration of tomcat in pom.xml
- Deploy the application using the following maven command
```
  mvn clean package tomcat7:deploy
```
## 👷‍ Contributors
- [Hesham Ahmed](https://github.com/heshmAhmed)
