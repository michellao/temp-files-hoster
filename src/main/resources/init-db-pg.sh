#!/usr/bin/env sh
set -e
createdb --username $POSTGRES_USER $DATABASE_NAME
cat ../schema.sql | psql -v ON_ERROR_STOP=1 --username $POSTGRES_USER --dbname $DATABASE_NAME