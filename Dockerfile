FROM bellsoft/liberica-openjre-alpine:21

LABEL org.opencontainers.image.source="https://github.com/michellao/temp-files-hoster"
LABEL org.opencontainers.image.licenses="MIT"

RUN adduser -Dh /app app
USER app
COPY --chown=app:app ./build/libs/shorturl-?.?.?.jar /app/shorturl.jar

WORKDIR /app

EXPOSE 8080
ENTRYPOINT ["java", "-Xms512M", "-Xmx1G", "-jar", "shorturl.jar"]