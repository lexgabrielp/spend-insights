CREATE
EXTENSION IF NOT EXISTS vector;
CREATE TABLE users
(
    id            UUID PRIMARY KEY,
    name          TEXT        NOT NULL,
    email         TEXT UNIQUE NOT NULL,
    password_hash TEXT        NOT NULL,
    role          TEXT        NOT NULL DEFAULT 'USER',
    enabled       BOOLEAN     NOT NULL DEFAULT true,
    created_at    TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE TABLE accounts
(
    id              UUID PRIMARY KEY,
    user_id         UUID NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    name            TEXT NOT NULL,
    institution     TEXT,
    currency        TEXT           DEFAULT 'PHP',
    opening_balance NUMERIC(14, 2) DEFAULT 0
);
CREATE TABLE categories
(
    id             UUID PRIMARY KEY,
    user_id        UUID REFERENCES users (id) ON DELETE CASCADE,
    name           TEXT    NOT NULL,
    color          TEXT,
    icon           TEXT,
    system_default BOOLEAN NOT NULL DEFAULT false
);
CREATE TABLE uploads
(
    id            UUID PRIMARY KEY,
    user_id       UUID        NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    filename      TEXT,
    content_type  TEXT,
    size_bytes    BIGINT               DEFAULT 0,
    status        TEXT        NOT NULL DEFAULT 'PENDING',
    error_message TEXT,
    created_at    TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE TABLE transactions
(
    id            UUID PRIMARY KEY,
    user_id       UUID           NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    account_id    UUID REFERENCES accounts (id),
    category_id   UUID REFERENCES categories (id),
    upload_id     UUID REFERENCES uploads (id),
    posted_at     DATE           NOT NULL,
    merchant      TEXT,
    amount        NUMERIC(14, 2) NOT NULL,
    type          TEXT           NOT NULL DEFAULT 'EXPENSE',
    currency      TEXT                    DEFAULT 'PHP',
    description   TEXT,
    source        TEXT,
    ai_confidence DOUBLE PRECISION,
    created_at    TIMESTAMPTZ    NOT NULL DEFAULT now()
);
CREATE INDEX idx_tx_user_date ON transactions (user_id, posted_at DESC);
CREATE INDEX idx_tx_merchant ON transactions (merchant);
CREATE TABLE ai_conversations
(
    id         UUID PRIMARY KEY,
    user_id    UUID        NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    title      TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE TABLE ai_messages
(
    id              UUID PRIMARY KEY,
    conversation_id UUID        NOT NULL REFERENCES ai_conversations (id) ON DELETE CASCADE,
    role            TEXT        NOT NULL,
    content         TEXT,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE TABLE embeddings
(
    id          UUID PRIMARY KEY,
    user_id     UUID        NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    entity_type TEXT        NOT NULL,
    entity_id   UUID        NOT NULL,
    content     TEXT,
    embedding   vector(768),
    created_at  TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE INDEX idx_embeddings_vector ON embeddings USING ivfflat (embedding vector_cosine_ops) WITH (lists = 100);
CREATE TABLE recurring_payments
(
    id               UUID PRIMARY KEY,
    user_id          UUID    NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    merchant         TEXT,
    average_amount   NUMERIC(14, 2),
    cadence          TEXT,
    next_expected_at DATE,
    confidence       DOUBLE PRECISION,
    active           BOOLEAN NOT NULL DEFAULT true
);
CREATE TABLE anomaly_reports
(
    id             UUID PRIMARY KEY,
    user_id        UUID             NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    transaction_id UUID REFERENCES transactions (id) ON DELETE CASCADE,
    score          DOUBLE PRECISION NOT NULL,
    reason         TEXT,
    severity       TEXT,
    created_at     TIMESTAMPTZ      NOT NULL DEFAULT now()
);
CREATE TABLE audit_logs
(
    id          UUID PRIMARY KEY     DEFAULT gen_random_uuid(),
    user_id     UUID REFERENCES users (id),
    action      TEXT        NOT NULL,
    entity_type TEXT,
    entity_id   UUID,
    ip_address  TEXT,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT now()
);
