FROM openjdk:8

COPY target/tinyurl-0.0.1-SNAPSHOT.jar /tinyurl-0.0.1-SNAPSHOT.jar

CMD ["java","-jar","tinyurl-0.0.1-SNAPSHOT.jar"]
