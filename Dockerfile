# Maven build container 

FROM maven:3.6.3-openjdk-11 AS ms-onbloarding-authentication-java

WORKDIR /tmp/

COPY pom.xml ./

COPY src ./src/

RUN mvn package

#pull base image

FROM openjdk

#expose port 8080
EXPOSE 8080

#default command
CMD java -jar /data/environment-java-0.1.0.jar

#copy hello world to docker image from builder image

COPY --from=ms-onbloarding-authentication-java /tmp/target/environment-java-0.1.0.jar /data/environment-java-0.1.0.jar
