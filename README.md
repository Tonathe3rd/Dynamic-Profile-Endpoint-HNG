# Description

A lightweight REST API built using **pure Java (no frameworks)**.  
It exposes a single GET endpoint `/me` that returns my profile information in JSON format.

---

## 🚀 Features
- Built using only core Java libraries (`com.sun.net.httpserver.HttpServer`)
- No external frameworks required
- Single endpoint returning a JSON response

---

## ⚙️ Requirements
- Java 8 or higher  
- IntelliJ IDEA (or any Java IDE)
- Git (for version control)
---

## 📦 Dependencies

| `com.sun.net.httpserver.HttpServer` | To create a lightweight HTTP server |

| `java.io` / `java.net` | For input/output and networking |

| `java.time` | For timestamps |

🟢 **No external libraries or package installations are required.**

---

## ⚙️ Environment Variables

No environment variables are required for this project.  

---

## 🛠️ Setup Instructions

### 1. Clone this repository
```bash
https://github.com/Tonathe3rd/Dynamic-Profile-Endpoint-HNG.git
cd Profile-Endpoint-HNG
```
2. Compile the Java file
```bash
javac ProfileServer.java
```
3. Run the server
```bash
java ProfileServer
```
You should see:
```bash
Server started on port 8080
```
🌐 Test the Endpoint
Open your browser or use curl:
```bash
http://localhost:8080/profile
```
You should receive the JSON response shown below:
```bash
{
  "status": "success",
  "user": {
    "email": "get2adeshola@gmail.com",
    "name": "Adeshola Adetona",
    "stack": "Java"
  },
  "timestamp": "2025-10-18T12:34:56.789Z",
  "fact": "Cats sleep for 70% of their lives."
}
```
If you see:
```bash
Server started on port 8080
```
Your API is running successfully 🎉

## 🧭Deployment
 Railway / Render / AWS Lightsail
 
See a sample of my deployment from Railway.app - 

👨🏽‍💻 Author - Adeshola Adetona

📧 get2adeshola@gmail.com

🕸️ Stack - Java

📜 License
This project is open-source under the MIT License.
