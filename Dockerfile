FROM adoptopenjdk:17-jre

WORKDIR /partner

COPY build/libs/seu_projeto.jar /partner/partner2.0.jar

EXPOSE 8080 8081 8082 1311

CMD ["java", "-jar", "partner2.0.jar"]
