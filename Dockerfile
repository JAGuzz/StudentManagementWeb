FROM amazoncorretto:21
LABEL mentainer="jaguzz98@gmail.com"
WORKDIR /app
COPY jarbuild/studentsApp-0.0.1-SNAPSHOT.jar /app/studentsApp-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "studentsApp-0.0.1-SNAPSHOT.jar"]