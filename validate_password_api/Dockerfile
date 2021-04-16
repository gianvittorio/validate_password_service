FROM openjdk:11

WORKDIR /opt/validate_password

COPY ./target/validate_password_api-0.0.1-SNAPSHOT.jar ./validate_password_api.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom",  "-jar", "./validate_password_api.jar"]
