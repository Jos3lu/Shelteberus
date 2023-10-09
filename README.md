# Shelteberus: Spring Cloud Project with Prometheus/Grafana, ELK, Sonar & Jenkins

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

