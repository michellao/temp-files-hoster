#!/usr/bin/env sh
set -e
cat schema.sql | psql -v ON_ERROR_STOP=1 --username $POSTGRES_USER --dbname $DATABASE_NAME
