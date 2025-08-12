# Just a another temporary file hoster

## Requirements

- MinIO server or AWS S3 instance
- PostgreSQL Database

## How to setup for development

You can use `docker-compose-dev.yaml` to start services

Copy from `.env.example` to `.env` and configure it according to your setup
```sh
cp .env.example .env
```

You need to enter the database username and password in the `.env`. This will create the PostgreSQL database using those credentials

```sh
docker compose -f docker-compose-dev.yaml up -d
```

Then you need to create MinIO key ID and access key on the web UI.

MinIO default credential for both: minioadmin

Go to http://localhost:9001/access-keys, create key and set it to .env

Launch an IDE like Intellij IDEA, it automatically detects the project and setup it.

## On production

### Using an image Docker

You need to configure an .env

```sh
docker run --env-file .env ghcr.io/michellao/temp-files-hoster
```
