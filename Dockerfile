FROM maven:3.6.0-jdk-8-slim

WORKDIR /usr/src/java-code
COPY . /usr/src/java-code
RUN mvn package -Dmaven.test.skip=true

WORKDIR /usr/src/java-app
RUN cp /usr/src/java-code/target/*.jar ./app.jar
EXPOSE 8081
CMD ["java", "-jar", "app.jar"]
