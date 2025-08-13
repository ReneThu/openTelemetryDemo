#!/bin/bash

if [ "$1" == "with-tracing" ]; then
  echo "Starting with OpenTelemetry and Jaeger..."
  docker-compose -f docker-compose.yml -f docker-compose-otel.yml up
elif [ "$1" == "no-tracing" ]; then
  echo "Starting without tracing..."
  docker-compose -f docker-compose.yml up
else
  echo "Usage: ./start.sh [no-tracing|with-tracing]"
fi
