FROM openjdk:11
ADD target/teamUp.jar teamUp.jar
EXPOSE 8443
ENTRYPOINT ["java","-jar", "teamUp.jar"]
