FROM openjdk:8-jdk-alpine

VOLUME /tmp
ADD sfs-course-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
EXPOSE 8088
ENV JAVA_OPTS=""
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}","/app.jar"]