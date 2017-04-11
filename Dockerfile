FROM maven:alpine

WORKDIR /ResumeBuilderBot
COPY pom.xml checkstyle.xml ./
COPY src src
RUN mvn compile assembly:single

CMD java -jar target/*.jar
