# URL Shortener System

## Solution Architecture & API Contract

**Version:** 1.0  
**Status:** Approved for Development

---

# Table of Contents

1. Executive Summary
2. Project Scope
3. Business Requirements
4. Functional Requirements
5. Non-Functional Requirements
6. Architecture Principles
7. Layered Architecture
8. Module Structure
9. End-to-End Flow
10. Snowflake ID Strategy
11. Base62 Encoding Strategy
12. Domain Model
13. Database Design
14. Redis Cache Design
15. API Standards
16. API Contracts
17. Response Standards
18. Error Standards
19. Validation Rules
20. Logging Standards
21. Monitoring & Observability
22. Security Considerations
23. Deployment Architecture
24. Development Guidelines
25. Future Enhancements
26. Final Sign-Off Decisions

---

# 1. Executive Summary

The URL Shortener Service is an enterprise-grade backend system designed to generate, manage, and resolve shortened URLs with high performance and scalability.

## Technology Stack

- Java 21
- Spring Boot 3.x
- Maven
- PostgreSQL
- Redis
- Snowflake ID Generator
- Base62 Encoding
- Flyway
- MapStruct
- OpenAPI (Swagger)
- Docker
- JUnit 5
- Mockito
- Testcontainers

## Objectives

- Generate globally unique short URLs
- Support fast URL redirection
- Maintain URL analytics
- Support caching for high traffic
- Follow enterprise architecture principles
- Be production deployment ready

---

# 2. Project Scope

## In Scope (MVP)

- Create Short URL
- Redirect Short URL
- Retrieve URL Details
- URL Analytics
- Soft Delete URL
- PostgreSQL Persistence
- Redis Cache
- Snowflake ID Generation
- OpenAPI Documentation
- Docker Deployment

## Out of Scope (Phase 2)

- Authentication
- Authorization
- User Management
- URL Expiry
- QR Code Generation
- Custom Aliases
- Kafka Events
- API Keys
- Rate Limiting
- Multi-Tenant Support

---

# 3. Business Requirements

## BR-001

Users should be able to create shortened URLs.

## BR-002

Users should be able to access original URLs through shortened URLs.

## BR-003

System should maintain analytics for URL usage.

## BR-004

System should support millions of URL redirections.

## BR-005

System should support future horizontal scaling.

---

# 4. Functional Requirements

## FR-001

Create Short URL

## FR-002

Redirect URL

## FR-003

Retrieve URL Details

## FR-004

Deactivate URL

## FR-005

Track Click Count

## FR-006

Track Last Access Timestamp

## FR-007

Retrieve URL Analytics

---

# 5. Non-Functional Requirements

## Availability

```text
99.9%
```

## Performance

Redirect Endpoint

```text
P95 < 50ms
```

## Scalability

```text
Millions of redirects
100K+ URLs
Horizontal scaling ready
```

## Reliability

```text
Redis failure must not impact service availability.
Fallback to PostgreSQL required.
```

## Maintainability

```text
Clean Architecture
Layer Separation
Dependency Inversion
```

---

# 6. Architecture Principles

## Rule 1

Web Layer must never access database directly.

## Rule 2

All business logic must reside in Domain Layer.

## Rule 3

Domain Layer must depend only on interfaces.

## Rule 4

Infrastructure implementations must remain replaceable.

## Rule 5

All APIs must be versioned.

## Rule 6

All timestamps must be stored in UTC.

## Rule 7

Soft delete only.

---

# 7. Layered Architecture

```text
                     +----------------------+
                     |  Web Integration     |
                     |  REST Controllers    |
                     +----------+-----------+
                                |
                                v
                     +----------------------+
                     |       Domain         |
                     |   Business Rules     |
                     +----------+-----------+
                                |
                   +------------+------------+
                   |                         |
                   v                         v

          +-----------------+      +-----------------+
          | PostgreSQL      |      | Redis Cache     |
          | Integration     |      | Integration     |
          +-----------------+      +-----------------+
```

---

# 8. Module Structure

```text
url-shortener

├── web-integration
│   ├── controller
│   ├── request
│   ├── response
│   └── advice
│
├── domain
│   ├── service
│   ├── model
│   ├── repository
│   ├── validator
│   └── exception
│
├── integration
│   ├── postgres
│   ├── redis
│   └── snowflake
│
├── configuration
│
└── common
    ├── constants
    ├── enums
    ├── utility
    └── model
```

---

# 9. End-to-End Flow

## URL Creation Flow

```text
Client
  |
  | POST /api/v1/urls
  v

Web Layer
  |
  v

Domain Layer
  |
  v

Snowflake Generator
  |
  v

Base62 Encoder
  |
  v

PostgreSQL Save
  |
  v

Redis Cache
  |
  v

Response
```

## Redirect Flow

```text
Client
  |
  | GET /K8xAVP
  v

Redis

CACHE HIT
   |
   v
Redirect URL

CACHE MISS
   |
   v

PostgreSQL
   |
   v

Update Redis
   |
   v

Redirect URL
```

---

# 10. Snowflake ID Strategy

## Purpose

Generate globally unique identifiers without relying on the database.

## Example

```text
1984573265187412
```

## Benefits

- Globally unique
- Distributed friendly
- High throughput
- No database dependency
- Horizontally scalable

---

# 11. Base62 Encoding Strategy

### Flow

```text
Snowflake ID
      ↓
Base62 Encode
      ↓
Short Code
```

### Example

```text
Snowflake ID

1984573265187412

↓

Base62

K8xAVP
```

### Character Set

```text
abcdefghijklmnopqrstuvwxyz
ABCDEFGHIJKLMNOPQRSTUVWXYZ
0123456789
```

### Result

```text
https://short.ly/K8xAVP
```

---

# 12. Domain Model

## Url

```java
Url
```

### Attributes

```text
id
snowflakeId
shortCode
originalUrl
clickCount
active
createdAt
updatedAt
lastAccessedAt
```

---

# 13. Database Design

## Table

```sql
CREATE TABLE url_mapping
(
    id BIGSERIAL PRIMARY KEY,

    snowflake_id BIGINT UNIQUE NOT NULL,

    short_code VARCHAR(20) UNIQUE NOT NULL,

    original_url TEXT NOT NULL,

    click_count BIGINT DEFAULT 0,

    active BOOLEAN DEFAULT TRUE,

    last_accessed_at TIMESTAMP,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL
);
```

## Indexes

```sql
CREATE UNIQUE INDEX idx_short_code
ON url_mapping(short_code);
```

```sql
CREATE UNIQUE INDEX idx_snowflake_id
ON url_mapping(snowflake_id);
```

---

# 14. Redis Cache Design

## Cache Key

```text
url:{shortCode}
```

Example:

```text
url:K8xAVP
```

## Cache Value

```json
{
  "originalUrl": "https://google.com",
  "active": true
}
```

## Cache Pattern

```text
Cache Aside Pattern

Read Redis
   ↓
Miss
   ↓
Read PostgreSQL
   ↓
Update Redis
   ↓
Return Response
```

---

# 15. API Standards

## Base Path

```http
/api/v1
```

## Content Type

```http
application/json
```

## Timestamp Standard

```text
ISO-8601 UTC
```

Example:

```text
2026-08-21T10:00:00Z
```

---

# 16. API Contracts

## API-001 Create URL

### Endpoint

```http
POST /api/v1/urls
```

### Request

```json
{
  "originalUrl": "https://www.google.com/search?q=springboot"
}
```

### Response

**HTTP 201**

```json
{
  "success": true,
  "message": "URL created successfully",
  "data": {
    "snowflakeId": 1984573265187412,
    "shortCode": "K8xAVP",
    "shortUrl": "https://short.ly/K8xAVP",
    "originalUrl": "https://www.google.com/search?q=springboot",
    "createdAt": "2026-08-21T10:00:00Z"
  }
}
```

---

## API-002 Redirect URL

### Endpoint

```http
GET /{shortCode}
```

### Example

```http
GET /K8xAVP
```

### Response

```http
HTTP/1.1 302 Found

Location:
https://www.google.com/search?q=springboot
```

---

## API-003 Get URL Details

### Endpoint

```http
GET /api/v1/urls/{shortCode}
```

### Response

```json
{
  "success": true,
  "data": {
    "snowflakeId": 1984573265187412,
    "shortCode": "K8xAVP",
    "originalUrl": "https://google.com",
    "clickCount": 125,
    "active": true,
    "createdAt": "2026-08-21T10:00:00Z",
    "updatedAt": "2026-08-21T11:00:00Z"
  }
}
```

---

## API-004 Delete URL

### Endpoint

```http
DELETE /api/v1/urls/{shortCode}
```

### Behaviour

```text
Soft Delete

active = false
```

### Response

```json
{
  "success": true,
  "message": "URL deactivated successfully"
}
```

---

## API-005 URL Analytics

### Endpoint

```http
GET /api/v1/urls/{shortCode}/analytics
```

### Response

```json
{
  "success": true,
  "data": {
    "shortCode": "K8xAVP",
    "clickCount": 125,
    "lastAccessedAt": "2026-08-21T11:10:00Z",
    "createdAt": "2026-08-21T10:00:00Z"
  }
}
```

---

# 17. Response Standards

## Success Response

```json
{
  "success": true,
  "message": "Request processed successfully",
  "data": {}
}
```

## Error Response

```json
{
  "success": false,
  "errorCode": "URL_NOT_FOUND",
  "message": "Short URL not found",
  "timestamp": "2026-08-21T10:00:00Z",
  "traceId": "b4f8e3d17f2a"
}
```

---

# 18. Error Standards

## INVALID_URL

```http
400 Bad Request
```

## URL_NOT_FOUND

```http
404 Not Found
```

## URL_INACTIVE

```http
409 Conflict
```

## INTERNAL_SERVER_ERROR

```http
500 Internal Server Error
```

---

# 19. Validation Rules

## Original URL

Requirements:

```text
Must start with http:// or https://
Maximum Length: 2048
Must be a valid URI
```

## Short Code

Requirements:

```text
Minimum Length: 5
Maximum Length: 20
```

Allowed Characters:

```text
a-z
A-Z
0-9
```

---

# 20. Logging Standards

## Logging Format

```text
INFO

Event=URL_CREATED
SnowflakeId=1984573265187412
ShortCode=K8xAVP
```

## Never Log

```text
Passwords
Secrets
Connection Strings
PII
```

---

# 21. Monitoring & Observability

## Tools

- Spring Boot Actuator
- Micrometer
- Prometheus
- Grafana

## Metrics

- Request Count
- Error Count
- API Latency
- Redis Hit Ratio
- Database Query Time
- JVM Metrics
- Memory Usage

---

# 22. Security Considerations

## Phase 1

- Input Validation
- Global Exception Handling
- Secure Configuration
- Secret Management
- API Versioning

## Phase 2

- Authentication
- Authorization
- API Keys
- Rate Limiting
- OWASP Hardening

---

# 23. Deployment Architecture

```text
Docker
   |
Spring Boot Container
   |
PostgreSQL Container
   |
Redis Container
```

## Cloud Options

### Hosting

- Railway
- Render
- OCI Free Tier

### PostgreSQL

- Neon

### Redis

- Upstash

---

# 24. Development Guidelines

## Java

```text
Java 21
```

## Framework

```text
Spring Boot 3.x
```

## Build Tool

```text
Maven
```

## Persistence

```text
Spring Data JPA
```

## Database Migration

```text
Flyway
```

## Object Mapping

```text
MapStruct
```

## Documentation

```text
OpenAPI / Swagger
```

## Testing

```text
JUnit 5
Mockito
Testcontainers
```

## Containerization

```text
Docker
Docker Compose
```

---

# 25. Future Enhancements

## Phase 2 Roadmap

- URL Expiry
- Custom Alias
- Authentication
- User Ownership
- API Keys
- QR Code Generation
- Kafka Event Publishing
- Click Analytics Dashboard
- Rate Limiting
- Multi-Tenant Support

---

# 26. Final Sign-Off Decisions

| Category | Decision |
|----------|----------|
| Architecture | 4-Layer Clean Architecture |
| Java Version | Java 21 |
| Framework | Spring Boot 3.x |
| Build Tool | Maven |
| ID Strategy | Snowflake ID |
| Short Code Strategy | Base62 Encoding |
| Database | PostgreSQL |
| Cache | Redis |
| Delete Strategy | Soft Delete |
| Documentation | OpenAPI |
| Deployment | Docker |
| API Version | v1 |

---

# Contract Status

✅ APPROVED FOR IMPLEMENTATION

This document serves as the baseline contract for architecture, design, development, testing, deployment, and future enhancements. Any implementation must comply with the defined architecture, API contracts, domain rules, response standards, and development guidelines.