FROM openjdk:11-ea-11-jdk
ARG JAR_FILE=target/library-management-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]