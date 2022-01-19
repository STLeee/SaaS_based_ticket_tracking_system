#!/bin/sh

version="1.0.1"

# npm run build
docker build -t scottli/tts-p1-frontend:$version .

# push to Docker Hub
docker push scottli/tts-p1-frontend:$version

# push to Google Artifact Registry
docker tag scottli/tts-p1-frontend:$version asia-east1-docker.pkg.dev/ticket-tracking-system/tts-p1/frontend:$version
docker push asia-east1-docker.pkg.dev/ticket-tracking-system/tts-p1/frontend:$version

read -p "done"