# 🛡️ FileScan — Virus Scanning Service Integration with Spring Boot and ClamAV

FileScan is a secure, asynchronous file scanning backend built with Spring Boot 3.  
It integrates with the ClamAV antivirus engine (via TCP socket or REST API) to automatically detect and handle malware in uploaded files.

The system accepts multipart file uploads, scans them in the background, logs every result, and blocks or quarantines infected files before they can be stored or processed further.

---

## 🧭 Overview

FileScan enables developers and system administrators to:

- Integrate real-time virus scanning into any backend workflow  
- Scan uploaded files asynchronously using ClamAV TCP or REST daemon  
- Reject or quarantine infected files automatically  
- Log scan outcomes for full audit and traceability  
- Notify administrators by email when an infection is detected (optional)

---

## 🧱 System Architecture — Main Components

| Component              | Technology           | Purpose                                   |
|------------------------|----------------------|-------------------------------------------|
| Backend Framework      | Spring Boot 3        | Core application & REST API               |
| Antivirus Engine       | ClamAV (TCP/REST)    | File scanning service                     |
| Database               | PostgreSQL           | Persist scan logs & file metadata         |
| Async Execution        | Spring Async         | Background virus scanning                 |
| File Storage           | Local FS             | Upload & quarantine folders               |
| Security               | Spring Security      | API key–based protection                  |
| Email Notifications    | Spring Mail          | (Optional) Alerts for infected files      |
| Mapping                | MapStruct            | DTO ↔ Entity clean mapping                |

---

## 🎯 Project Requirements & Implementation

| Requirement             | Implementation                                                                                |
|-------------------------|------------------------------------------------------------------------------------------------|
| Multipart File Upload   | Implemented via `FileUploadController` using `MultipartFile                                  |
| ClamAV Integration      | TCP mode (`ClamAVTcpClient`) and REST mode (`ClamAVRestClient`) — configurable via clamav.mode |
| Asynchronous Scanning   | Background scan using `@Async("scanExecutor")`                                                |
| Infection Handling      | Infected files moved to `quarantine` and marked `INFECTED`                                    |
| Scan Logging            | Implemented via `ScanLogService`, stored in PostgreSQL                                        |
| Error Handling          | Managed in `GlobalExceptionHandler`                                                           |
| Connection Failures     | Logged and handled in `FileScanServiceImpl`                                                   |
| Email Notifications     | Implemented using Spring Mail (optional)                                                      |
| Quarantine Management   | Implemented in `QuarantineServiceImpl`                                                        |

---

## 🧩 Must-Have Features

### 📤 1. Multipart File Upload
- Endpoint: `POST /api/files/upload`  
- Accepts `multipart/form-data`  
- Starts async scanning  
- Returns `202 Accepted` immediately  

### 🧠 2. ClamAV Integration
Supports:
- **TCP mode** → `clamav.mode=TCP`
- **REST mode** → `clamav.mode=REST`

Fully configurable in `application.properties`.

### ⚡ 3. Asynchronous Scanning
- File scan runs in background via `@Async`  
- User immediate response, scan continues  

### 🚫 4. Infection Handling
- Infected files moved to `/data/quarantine`  
- Scan logs updated as `INFECTED`  

### 🧾 5. Scan Result Logging
Each log contains:
- Trace ID  
- Filename  
- Status (`STARTED`, `CLEAN`, `INFECTED`, `FAILED`)  
- Duration  
- Message / ClamAV output  

---

## 💡 Bonus Features Implemented

| Feature                  | Description                                       | Status |
|--------------------------|---------------------------------------------------|--------|
| 🧳 Quarantine Management | Moves infected files to a quarantine directory    | ✔️     |
| 📧 Email Notifications   | Sends email alerts for infected file detections   | ✔️     |

---

## 🧰 Technical Stack

| Category          | Tools / Libraries       |
|-------------------|--------------------------|
| Language          | Java 17                 |
| Framework         | Spring Boot 3           |
| Database          | PostgreSQL              |
| Security          | Spring Security (API Key) |
| Async Execution   | Spring @Async           |
| Mapping           | MapStruct               |
| Virus Engine      | ClamAV TCP / REST       |
| Build Tool        | Maven                   |
| Mail (Optional)   | Spring Mail             |

---

## 🔒 Security Highlights

X-API-KEY: dev-secret-key

Invalid or missing key → `401 Unauthorized`.

A global exception handler ensures consistent JSON error responses.

---

## 🚀 How to Run Locally

### 1️⃣ Prerequisites
Install:
- Java 17  
- Maven  
- PostgreSQL  
- ClamAV daemon (port 3310)

### 2️⃣ Database
Create a PostgreSQL database and update:

spring.datasource.url=...
spring.datasource.username=...
spring.datasource.password=...


### 3️⃣ Configure ClamAV
Start ClamAV daemon:

sudo systemctl start clamav-daemon 

### 4️⃣ Run App

---

## 🧾 API Endpoints

| Method | Endpoint               | Description                       |
|--------|-------------------------|-----------------------------------|
| POST   | `/api/files/upload`    | Uploads a file & starts async scan |
| GET    | `/api/logs`            | List scan logs (paged)            |
| GET    | `/api/quarantine`      | List quarantined files (bonus)    |

---

## 🧪 Sample Upload Request


curl -X POST "http://localhost:2122/api/files/upload" \
  -H "X-API-KEY: dev-secret-key" \
  -F "file=@/path/to/sample.pdf"

  🔗 Swagger UI

http://localhost:2122/swagger-ui/index.html


Made with  by Xadija Pashayeva



