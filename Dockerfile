FROM bellsoft/liberica-openjre-alpine:21

RUN adduser -Dh /app app
USER app
COPY ./build/libs/shorturl-0.0.1-SNAPSHOT.jar /app

WORKDIR /app
RUN mkdir database

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "shorturl-0.0.1-SNAPSHOT.jar"]