# Package stage
FROM openjdk:17-oracle
EXPOSE 8080
COPY /build/libs/*.jar /vgcodetest-0.0.1.jar
ENTRYPOINT ["java","-jar","/vgcodetest-0.0.1.jar"]



