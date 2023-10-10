FROM openjdk:17
ADD dogs/target/*.jar dogs.jar
ADD volunteers/target/*.jar volunteers.jar
ADD users/target/*.jar users.jar
ADD adoptions/target/*.jar adoptions.jar

ENTRYPOINT ["bash", "start.sh"]