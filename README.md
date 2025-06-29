# 📂 Spring Boot File Manager Microservice

A modular, secure, and scalable file manager microservice built with:

* 🚀 **Spring Boot** (REST API)
* 💢 **PostgreSQL** (File metadata DB)
* 🪣 **MinIO** (Object storage)
* 🔐 **Keycloak** (OAuth2 / SSO)
* 🐳 **Docker Compose** (DevOps)

---

## 🔧 Features

✅ Upload, download, list, and delete files
✅ Store and search metadata (filename, type, size, user, folder)
✅ Secure access via **JWT (Keycloak)**
✅ Supports multi-tenancy and directory structure
✅ Local or cloud deployment (MinIO/S3)
✅ Swagger UI for testing

---

## 📦 Architecture

```
Client → Spring Boot API → [PostgreSQL + MinIO]
                   ↳
                 Keycloak (Auth)
```

---

## 📁 Project Structure

```
src/
├── controller/       → File REST APIs
├── service/          → Business logic
├── model/            → JPA Entities
├── repository/       → Spring Data JPA
├── config/           → Security, MinIO, Keycloak config
└── exception/        → Global error handling
```

---

## 🚀 Getting Started (Dev)

### 1. Clone this repo

```bash
git clone https://github.com/ichamrong/spring-boot-file-manager
cd spring-boot-file-manager
```

### 2. Start services with Docker Compose

```bash
docker-compose up --build
```

This will start:

* PostgreSQL on `5432`
* MinIO on `9000` (`9090` console)
* Keycloak on `8080`
* Spring Boot app on `8081`

### 3. Access URLs

| Service     | URL                                                                            |
| ----------- | ------------------------------------------------------------------------------ |
| API Docs    | [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html) |
| MinIO UI    | [http://localhost:9090](http://localhost:9090)                                 |
| Keycloak UI | [http://localhost:8080](http://localhost:8080)                                 |

---

## 🔐 Authentication (Keycloak)

### Default Admin Credentials:

* Username: `admin`
* Password: `admin`

> You must create:
>
> * A Realm (e.g., `file-service`)
> * A Client (confidential, with `client_id` = `file-manager`)
> * Roles: `user`, `admin`

---

## 📄 Sample API Endpoints

| Method | Endpoint           | Description            |
| ------ | ------------------ | ---------------------- |
| POST   | `/api/files`       | Upload a file          |
| GET    | `/api/files/{id}`  | Download a file        |
| DELETE | `/api/files/{id}`  | Soft-delete a file     |
| GET    | `/api/files`       | List files (paginated) |
| POST   | `/api/directories` | Create directory       |

---

## ⚙️ Configuration

Edit `src/main/resources/application.yml` or use env vars:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://postgres:5432/filedb
    username: fileuser
    password: filepass

minio:
  url: http://minio:9000
  access-key: minio
  secret-key: minio123
  bucket: uploads

keycloak:
  realm: file-service
  auth-server-url: http://keycloak:8080
  resource: file-manager
```

---

## 🧪 Testing

```bash
./mvnw test
```

---

## 👰 Dev Tools

* ✅ Spring DevTools
* 🧪 Testcontainers (optional)
* 🔍 Swagger + OpenAPI
* 🐘 Flyway for DB migration

---

## 📜 License

MIT License — feel free to fork, extend, and use.

---

## 🤝 Contribution

Pull requests and issues are welcome!
Please open a discussion if you'd like to suggest features.

---

## ✨ Credits

Built with ❤️ by @ichamrong
