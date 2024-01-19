### 🚀 Java Grading Assistant System

Experience the power of my Java project that leverages a REST API to efficiently store and manage data. This project focuses on establishing and managing student-teacher relationships within various classes, with a special emphasis on assignments. Each class is represented by Java entities such as Assignment, AssignmentStatistics, AssignmentSubmission, Course, GradingJob, GradingPolicy, JobStatus, Question, QuestionSolution, QuestionStatistics, QuestionType, and Student.

### 🔧 Key Features:

REST API Integration: Seamlessly interact with the system through a robust REST API.
Student-Teacher Relationships: Establish and maintain relationships for effective collaboration.
Grading Assistant Code: Recent additions include Grading Assistant Code for enhanced functionality.
Data Management: Efficiently store and retrieve data for various components like assignments and questions.
Explore the intricacies of the Grading Assistant System, dive into the codebase, and witness the seamless orchestration of student-teacher dynamics.🌟


### Before running the project
- Install JDK 17
- Install MongoDB Community Edition
- Install Docker Desktop
- Install Postman

---

### How to build the project and run automated tests

Make sure local MongoDB is running

./mvnw clean package

### How to run the project in dev mode from command line

./mvnw spring-boot:run -Dspring.profiles.active=dev

### How to stop the project in dev mode from command line

On the same console where project was started: ctrl + c

---
### How to run the project in test mode by creating and running containers

Make sure Docker Desktop is running

./mvnw clean package -DskipTests

docker compose up -d

### How to check logs in test mode

docker compose logs

### How to stop the project in test mode

docker compose stop

### How to restart the project in test mode

docker compose start

### How to stop the project in test mode and removing containers

docker compose down

---
### List all containers
docker ps -a

### Remove all stopped containers
docker rm $(docker ps --filter status=exited -q)

### List volumes
docker volume ls

### Remove all unused local volumes
docker volume prune

### List images
docker images

### Remove image by Id
docker rmi ${imageId}

---
### Resources
- [Spring Data MongoDB - Reference Documentation] (https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/)
- [Building REST services with Spring] (https://spring.io/guides/tutorials/rest/)
- [Docker Compose CLI] (https://docs.docker.com/compose/reference/)
- [Docker CLI] (https://docs.docker.com/engine/reference/commandline/cli/)
