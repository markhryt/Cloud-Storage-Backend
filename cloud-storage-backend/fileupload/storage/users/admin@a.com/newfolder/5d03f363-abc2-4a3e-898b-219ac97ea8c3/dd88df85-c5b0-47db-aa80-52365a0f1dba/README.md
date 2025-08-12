# 🌩️ Cloud Storage Backend

This project is an implementation of a simplified cloud-based file storage system. It supports secure file uploads and downloads, authentication, metadata management, fault tolerance, and simulated distributed storage.

---

## 🚀 Features

- 🔐 **Authentication** – Basic user login and token-based authorization.
- 📁 **File Upload/Download** – REST endpoints for storing and retrieving files.
- 🧾 **Metadata Tracking** – File metadata stored and queryable (e.g., size, type, owner, timestamp).
- 🗃️ **Simulated Distributed Storage** – Files split across mock storage nodes.
- 🔄 **Fault Tolerance** – Redundancy and recovery strategies using replication.
- 📤 **Secure File Sharing** – Shareable links or access tokens for shared files.

---

## 🛠️ Tech Stack

- **Java 24**
- **Spring Boot** (REST APIs, validation, security)
- **JUnit & Mockito** for testing
- **MySQL** (for metadata storage)
- **Local File System or In-Memory Mock Nodes** (for simulating storage)
- **JWT** for authentication

---

## 🧠 Project Goals

This project was built to deepen understanding of:
- Backend systems architecture
- Distributed storage concepts
- Designing scalable and testable APIs
- Security and file access control

---

## 📦 API Endpoints

| Method | Endpoint                  | Description              |
|--------|---------------------------|--------------------------|
| POST   | `/auth/register`          | Register new user        |
| POST   | `/auth/login`             | Authenticate user        |
| POST   | `/files/upload`           | Upload file              |
| GET    | `/files/{id}/download`    | Download file by ID      |
| GET    | `/files/metadata`         | Query metadata           |
| POST   | `/files/share`            | Generate shareable link  |

---

## 🧪 Tests

Unit and integration tests included for:
- Authentication flow
- File upload/download
- Metadata persistence
- Fault recovery

