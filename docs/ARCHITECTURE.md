# Architecture

Spend Insights is a local-first monorepo with a Spring Boot API, PostgreSQL/pgvector persistence, Ollama local
inference, and a Next.js UI. All AI requests are routed to the local Ollama container. The backend owns auth,
validation, ingestion, classification, summaries, and chat orchestration. The frontend is a thin authenticated client.

Layers: controller -> service -> repository -> database / Ollama adapter.

Security: stateless JWT access tokens, BCrypt password hashing, role authorities, CORS allowlist, validation, secure
headers, JPA parameterization, and centralized error handling.
