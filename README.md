🛡️ FileScan — Virus Scanning Service Integration with Spring Boot and ClamAV

FileScan is a secure, asynchronous file scanning backend built with Spring Boot 3.
It integrates with the ClamAV antivirus engine (via TCP socket or REST API) to automatically detect and handle malware in uploaded files.

The system accepts multipart file uploads, scans them in the background, logs every result, and blocks or quarantines infected files before they can be stored or processed further.

🧭 Overview

FileScan enables developers and system administrators to:

Integrate real-time virus scanning into any backend workflow.

Scan uploaded files asynchronously using ClamAV TCP or REST daemon.

Reject or quarantine infected files automatically.

Log scan outcomes for full audit and traceability.

Notify administrators by email when an infection is detected (optional).

🧱 System Architecture
Əsas Komponentlər
Component	Technology	Purpose
Backend Framework	Spring Boot 3	Core application and REST API
Antivirus Engine	ClamAV (TCP / REST)	File scanning service
Database	PostgreSQL	Persist scan logs and file metadata
Asynchronous Execution	Spring Async Executor	Background virus scanning
File Storage	Local File System	Upload and quarantine directories
Security	Spring Security (API Key)	Protects API endpoints
Email Notifications	Spring Mail (Optional)	Sends alerts for infected files
Mapping	MapStruct	Clean object mapping between DTOs and entities

🎯 Project Requirements & How They Were Implemented
Requirement	Implementation
Multipart File Upload	Implemented via FileUploadController using MultipartFile.
ClamAV Integration	Two client modes: TCP (ClamAVTcpClient) and REST (ClamAVRestClient) — configurable via clamav.mode.
Asynchronous Scanning	File scanning executed in background using @Async("scanExecutor").
Infection Handling	Infected files moved to quarantine folder, marked as INFECTED.
Scan Result Logging	Implemented via ScanLogService and persisted in PostgreSQL.
Error Handling	Graceful error responses handled by GlobalExceptionHandler.
Connection Failures	Caught and logged with fallback handling in FileScanServiceImpl.
Email Notifications (Bonus)	Using Spring Mail in EmailNotificationServiceImpl.
Quarantine Management (Bonus)	QuarantineServiceImpl moves infected files to separate quarantine path.

🧩 Must-Have Features
📤 Multipart File Upload

Accepts files via POST /api/files/upload

Validates request

Starts async scanning

Returns 202 Accepted immediately

🧠 ClamAV Integration

Supports two scanning modes:

TCP mode → clamav.mode=TCP

REST mode → clamav.mode=REST

Fully configurable through application.properties.

⚡ Asynchronous Scanning

Scanning happens in parallel using Spring @Async

User gets immediate confirmation while backend scans file in background

🚫 Infection Handling

Infected files are automatically moved to /data/quarantine

Scan logs updated with status INFECTED

🧾 Scan Result Logging

Each scan includes:

Trace ID

Filename

Status (STARTED, CLEAN, INFECTED, FAILED)

Duration (ms)

Message or ClamAV response

💡 Bonus Features Implemented
🧳 Quarantine Management

Moves infected files to a separate directory for investigation

📧 Email Notifications

Sends automatic email alerts for infected files (if enabled)

🧰 Technical Stack
Category	Tools / Libraries
Language	Java 17
Framework	Spring Boot 3
Database	PostgreSQL
Security	Spring Security (API Key)
Async Execution	Spring @Async
Mapping	MapStruct
Build Tool	Maven
Virus Engine	ClamAV TCP / REST
Mail (Optional)	Spring Boot Mail
🔒 Security Highlights

All endpoints protected via API key header:

X-API-KEY: dev-secret-key


Missing/invalid key → 401 Unauthorized

Global exception handler ensures uniform JSON responses

🚀 How to Run Locally
1️⃣ Prerequisites

Install:

Java 17

Maven

PostgreSQL

ClamAV daemon (port 3310)

2️⃣ Database

Create PostgreSQL database (e.g., postgres)
Update credentials in application.properties.

3️⃣ Configure ClamAV

Ensure ClamAV daemon is running:

sudo systemctl start clamav-daemon

4️⃣ Run the Application
mvn spring-boot:run

🧾 Example API Endpoints
Method	Endpoint	Description
POST	/api/files/upload	Uploads file and starts async scan
GET	/api/logs	Lists scan logs (paged)
GET	/api/quarantine	Lists quarantined files (bonus)
🧪 Sample Upload Request
curl -X POST "http://localhost:2122/api/files/upload" \
-H "X-API-KEY: dev-secret-key" \
-F "file=@/path/to/sample.pdf"
----------------------------------------------------------------
🔗 Swagger:http://localhost:2122/swagger-ui/index.html
----------------------------------------------------------------
📬 Contact

Made with  by Xadija Pashayeva

📧 Email: xadijapashayeva@gmail.com

🔗 LinkedIn: https://www.linkedin.com/in/xadija-pashayeva