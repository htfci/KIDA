# KIDA

## About
KIDA (Knowledge-Integrated Document Assistant) is an intelligent document processing prototype that pairs uploaded files with internal and external ground truth. It automates file-based workflows by offering both dynamic, single-click task execution and interactive, document-grounded Q&A.

## System Architecture

```mermaid
flowchart TD
    UI["Split-View UI<br/><i>(Angular)</i>"]
    API["Orchestrator<br/><i>(Spring Boot)</i>"]
    DB[("Internal knowledge<br/><i>(PostgreSQL)</i>")]
    AI["AI Service<br/><i>(Stateless LLM Logic)</i>"]
    UI -.->|"HTTP / WebSocket<br/>(streaming tokens)"| AI

    UI <-->|"HTTP / Multipart Upload"| API
    API <-->|"SQL (JPA)"| DB
    API <-->|"REST (HTTP / gRPC)"| AI
```
