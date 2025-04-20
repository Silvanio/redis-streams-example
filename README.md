
# Redis Streams with Spring Boot (Java 21)

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring--Boot-3.x-brightgreen)
![Redis](https://img.shields.io/badge/Redis-Streams-red)

This project demonstrates how to integrate **Redis Streams** with a **Spring Boot (Java 21)** application using **Spring Data Redis**. It includes examples of **publishing** and **consuming** messages using Redis Streams in a microservice-oriented architecture.

---

## 🔄 What Are Redis Streams?

Redis Streams is a powerful data structure introduced in Redis 5.0. It enables log-like, append-only data streaming with consumer group support, persistence, and high performance — ideal for real-time processing and event-driven systems.

---

## 🧠 Key Benefits of Redis Streams

- **High throughput and low latency** message processing
- Built-in support for **consumer groups** and **acknowledgment**
- Message **persistence** (unlike traditional pub/sub)
- Ideal for **microservices communication** and **event sourcing**
- Lightweight alternative to Kafka for simpler use cases
- Supports **message replay**, **scalability**, and **back-pressure**

---

## 📊 Architecture Overview

Below is a simple architecture showing how a Spring Boot microservice integrates with Redis Streams:


> *Producer services write events to Redis Streams. Consumer services listen to these streams, using consumer groups for parallel processing.*

---

## 🚀 How to Run the Project (Step by Step)

### 1️⃣ Step 1 – Start Redis using Docker

```bash
docker run -d --name redis-streams -p 6379:6379 redis:7
```

---

### 2️⃣ Step 2 – Clone the Repository

```bash
git clone https://github.com/Silvanio/redis-streams-example.git
cd redis-streams-example
```

---

### 3️⃣ Step 3 – Build the Project with Maven

Using the Maven wrapper:

```bash
./mvnw clean install
```

Or with Maven installed locally:

```bash
mvn clean install
```

---

## 📁 Project Structure

```bash
src/
├── config/           # Redis configuration and bean setup
├── producer/         # Publisher logic
├── consumer/         # Listener and message consumer
├── data/             # Models utilities
└── RedisStreamsExempleApplication.java
```

---

## 🧪 Redis CLI Example

To manually inspect Redis Streams via CLI:


```bash
curl -X POST http://localhost:8080/example \
-H "Content-Type: application/json" \
-d '{"message":"Your message here"}'
```
---

## 🛠 Technologies Used

- **Java 21**
- **Spring Boot 3.x**
- **Spring Data Redis**
- **Redis 7+**
- **Docker**
- **Maven**

---

## 📥 Example Use Cases

- Order/Event Processing Pipelines
- Real-time Notification Systems
- IoT Sensor Data Collection
- Log/Event Aggregation
- Lightweight Messaging between Microservices

---
