# Shelteberus: Spring Cloud Project (Prometheus/Grafana, ELK, Sonar & Jenkins)

![diagram.png](images/diagram.png)

## Microservices
### Users
- Responsible for managing users
### Dogs
- Responsible for managing dogs
### Volunteers
- Responsible for managing volunteers
### Adoptions
- Responsible for managing adoptions

![microservices.png](images/microservices.png)

## Installation
1. ``maven clean install`` to install the project dependencies
2. ``docker-compose up --build`` to build up Prometheus, Grafana, ELK, Jenkins, Rabbitmq, Zipkin & PostgreSQL
3. Run the Sonarqube container: ``docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest``
4. Start the Config Server from the IDE
5. Start the Eureka Server from the IDE
6. Start the Gateway Server from the IDE
7. Start the microservices (users, dogs, volunteers & adoptions) from the IDE

## Documentation
### Config Server
- Store the configurations for all microservices in the application
- Each microservice doesn't need to store its configuration

### Eureka server
- Service discovery for microservices
- Contains all the information about client microservices running on which IP address and port
- [Eureka URL](http://localhost:8761/)

### Gateway
- A simple, yet effective way to route to APIs
- Retrieve data from multiple services with a single request
- [Gateway URL](http://localhost:8081/)

### Zipkin
- Distributed tracing
- Provides mechanisms for sending, receiving, storing & visualizing traces
- [Zipkin URL](http://localhost:9411/)

### Prometheus
- Open-source monitoring solution for collecting and aggregating metrics as time series data
- Each item in a Prometheus store is a metric event accompanied by the timestamp it occurred
- [Prometheus URL](http://localhost:9090/)

### Grafana
- Tool for visualizing and analyzing data from various sources
- Lets you keep tabs on application performance and error rates
- [Grafana URL](http://localhost:3000/)

### Filebeat
- Lightweight shipper for forwarding and centralizing log data
- Can send data directly to Elasticsearch or via Logstash

### Logstash
- Tool that can extract, transform, and load the data using filters and plugins
- It collects data from different sources and send to multiple destinations

### Elasticsearch
- An open-source search and analytics engine
- Proficient in managing colossal volumes of data, delivering the precise information we seek

### Kibana
- An open-source data visualization dashboard for Elasticsearch
- It provides visualization capabilities on top of the content indexed on an Elasticsearch cluster
- [Kibana URL](http://localhost:5601/)

### Jenkins
- Tool used for automation
- Open-source server that allows all the developers to build, test and deploy software
- [Jenkins URL](http://localhost:8080/)

### Users API
- Get users
- Get user by ID
- Create user
- Update user
- Delete user

### Dogs API
- Get dogs
- Get dog by ID
- Get reserved dogs of a user
- Get dogs associated to a volunteer
- Create dog
- Reserve a dog
- Cancel a reserve
- Delete a dog

### Volunteers API
- Get volunteers
- Get volunteer by ID
- Create volunteer
- Update volunteer
- Add dog to volunteer
- Delete dog from volunteer
- Delete volunteer

### Adoptions API
- Get adoptions
- Get adoption by ID
- Get adoption by user ID
- Create adoption
