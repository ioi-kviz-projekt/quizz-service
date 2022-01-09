# quizz-service

## Build & Deploy

1. Build project `mvn clean package`
2. Build docker image `docker build -t quizz-service:latest .`
3. Deploy stack (service + database) using docker compose `docker-compose up -d`
4. Service is now listening on `localhost:8080/*`

