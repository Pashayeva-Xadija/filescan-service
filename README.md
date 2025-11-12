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
ClamAV Integration	Two client modes: TCP (ClamAVTcpClient) and REST (ClamAVRestClient). Configurable via clamav.mode.
Asynchronous Scanning	File scanning executed in background using @Async("scanExecutor").
Infection Handling	Infected files moved to quarantine folder, marked as INFECTED.
Scan Result Logging	Implemented via ScanLogService and persisted in PostgreSQL.
Error Handling	Graceful error responses handled by GlobalExceptionHandler.
Connection Failures	Caught and logged with fallback handling in FileScanServiceImpl.
Email Notifications (Bonus)	Implemented via EmailNotificationServiceImpl using Spring Mail.
Quarantine Management (Bonus)	QuarantineServiceImpl moves infected files to separate quarantine path.
🧩 Must-Have Features
📤 Multipart File Upload

Securely accepts files via POST /api/files/upload.

Validates request, starts async scanning job, and returns immediate response (202 Accepted).

🧠 ClamAV Integration

Supports both TCP and REST modes:

clamav.mode=TCP → uses socket client.

clamav.mode=REST → uses HTTP client.

Fully configurable in application.properties.

⚡ Asynchronous Scanning

Non-blocking scanning handled by Spring’s @Async executor.

Users receive immediate confirmation while the backend scans in parallel.

🚫 Infection Handling

Infected files are quarantined automatically under /data/quarantine.

ScanLog entries are updated with INFECTED status and details.

🧾 Scan Result Logging

Each scan is logged with:

Trace ID

Filename

Status (STARTED, CLEAN, INFECTED, FAILED)

Duration (ms)

Message or ClamAV response

💡 Bonus Features Implemented
🧳 Quarantine Management

Infected files are moved to a separate quarantine directory.

Logged for future inspection or deletion.

📧 Email Notifications

When enabled, admin(s) receive automatic alerts when infections are found.

Configured via mail.enabled=true.

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

All endpoints protected by an API key header:

X-API-KEY: dev-secret-key


Invalid or missing key → returns 401 Unauthorized.

Clean GlobalExceptionHandler provides uniform JSON responses.

🚀 How to Run Locally
1️⃣ Prerequisites

Install:

Java 17

Maven

PostgreSQL

ClamAV daemon (running on port 3310)

2️⃣ Database

Create a PostgreSQL database (e.g., postgres) and update credentials in application.properties.

3️⃣ Configure ClamAV

Ensure clamd service is active:

sudo systemctl start clamav-daemon

4️⃣ Run the Application
mvn spring-boot:run

🧾 Example API Endpoints
Method	Endpoint	Description
POST	/api/files/upload	Uploads file and starts async virus scan
GET	/api/logs	Lists scan logs (paged)
GET	/api/quarantine	Lists quarantined files (bonus)
Sample Upload Request
curl -X POST "http://localhost:8080/api/files/upload" \
-H "X-API-KEY: dev-secret-key" \
-F "file=@/path/to/sample.pdf"
--------------------------------------------------------------------------
📬 Contact:

Made with Xadija Pashayeva: 📧 xadijapashayeva@gmail.com

LinkedIn: https://www.linkedin.com/in/xadija-pashayeva