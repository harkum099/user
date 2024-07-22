FROM adoptopenjdk/openjdk17:alpine-jre

EXPOSE 8088

ADD target/card-0.0.1-SNAPSHOT.jar app1.jar

ENTRYPOINT ["java","-jar","/app1.jar"]
