# Just a another temporary file hoster

## Requirements

You need an instance of AWS S3 or an endpoint S3 like MinIO.

## How to setup for development

Configure your .env from .env.example

```sh
cp .env.example .env
```

Launch an IDE like Intellij IDEA, it automatically detects the project and setup it.

## On production

### Using an image Docker

You need to configure an .env

```sh
docker run --env-file .env ghcr.io/michellao/file-temp-hoster
```
