# 🧾 Order Management System  
> Spring Boot + Kafka + Redis + PostgreSQL + Prometheus + Grafana (All Dockerized)

## 🔍 Overview  
This is a scalable, event-driven **Order Management System** built with **Spring Boot**, leveraging **Kafka** for asynchronous messaging, **Redis** for caching, **PostgreSQL** for storage, and **Prometheus + Grafana** (Dockerized) for real-time monitoring. Designed to simulate production-ready patterns like event sourcing, background processing, and observability — all in a containerized setup.

---

## 🚀 Features

- 🧾 **CRUD Operations** – Manage orders with full lifecycle support
- 📦 **Order Events via Kafka** – Events like `ORDER_PLACED`, `ORDER_SHIPPED`, etc.
- ⚡ **Redis Caching** – Speed up reads on frequent endpoints
- 💾 **PostgreSQL** – Persistent, relational storage
- 📡 **Prometheus + Grafana** – Metrics and dashboards via Docker

---

## ⚙️ Tech Stack

| Layer        | Tech                                |
|--------------|-------------------------------------|
| **Backend**  | Spring Boot, Spring Data JPA, Kafka |
| **Storage**  | PostgreSQL                          |
| **Cache**    | Redis                               |
| **Events**   | Apache Kafka                        |
| **Monitoring** | Spring Actuator, Prometheus, Grafana |
| **Containers** | Docker & Docker Compose           |
| **JUnit & Mockito** | Automated Testing           |

---

## 🛠️ Setup Instructions

### 🔁 1. Clone the repository

```bash
git clone https://github.com/yourusername/order-management.git
cd order-management
```

### 🐳 2. Start all services using Docker Compose

```bash
docker-compose up -d
```

This will start:
- Kafka + Zookeeper
- Redis
- PostgreSQL
- Prometheus
- Grafana

### ⚙️ 3. Backend Configuration (`application.properties`)

```properties
spring.datasource.url=jdbc:postgresql://postgres:5432/orders_db
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.redis.host=redis
spring.redis.port=6379

spring.kafka.bootstrap-servers=kafka:9092
spring.kafka.consumer.group-id=order-group

spring.cache.type=redis

management.endpoints.web.exposure.include=*
management.endpoint.prometheus.enabled=true
```

---

## 🌐 Access Points

| Service    | URL                         |
|------------|-----------------------------|
| Backend API       | http://localhost:8080       |
| Prometheus        | http://localhost:9090       |
| Grafana Dashboard | http://localhost:3000       |

---

## 🔌 API Endpoints

| Method | Endpoint            | Description             |
|--------|---------------------|-------------------------|
| GET    | /api/orders         | Fetch all orders        |
| GET    | /api/orders/{id}    | Fetch order by ID       |
| POST   | /api/orders         | Create new order (triggers Kafka event) |
| PUT    | /api/orders/{id}    | Update order            |
| DELETE | /api/orders/{id}    | Delete/cancel order     |

---

## 📬 Kafka Topics

| Topic Name        | Purpose                         |
|-------------------|----------------------------------|
| `orders`   | Event published on order update     |

---

## ⚡ Performance Metrics

| Scenario             | Without Redis | With Redis |
|----------------------|---------------|------------|
| Fetch Order by ID    | ~300ms        | ~40ms      |
| List Recent Orders   | ~600ms        | ~90ms      |
| Bulk Order Creation  | ~12s          | ~3s        |
| Kafka Event Publish  | ~15ms         | ~10ms      |

---

## 🧪 Common Issues & Fixes

| Issue                                 | Resolution                          |
|--------------------------------------|--------------------------------------|
| PostgreSQL not connecting            | Use service name `postgres` in config |
| Kafka consumer errors                | Set `group-id` and check topic configs |
| Prometheus not scraping              | Expose Spring Boot `/actuator/prometheus` |
| Redis not caching updated records    | Apply `@CachePut` or `@CacheEvict` annotations |
| Grafana port conflict                | Use default Grafana on 3000 (no frontend) |

---

## 🔐 Future Enhancements

- JWT-based authentication & role-based access
- Kafka dead-letter queues & retry strategies
- Advanced Prometheus alerting
- Horizontal scaling via Docker Swarm or Kubernetes

---
![Grafana Dashboard Screenshot](/grafana_output.png)
---

## 📌 Conclusion  
This system demonstrates a complete backend solution with event-driven architecture, caching, observability, and scalable design — ideal for real-world applications with distributed components.

---

## 🤝 Contributions  
Open to feedback, bug reports, and PRs. Let’s make this better together!
