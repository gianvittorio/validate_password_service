FROM openjdk:11

EXPOSE 8081/tcp

WORKDIR /opt/validate_password

COPY ./target/validate_password_api-0.0.1-SNAPSHOT.jar ./validate_password_api.jar

ENTRYPOINT ["java", "-jar", "./validate_password_api.jar"]
