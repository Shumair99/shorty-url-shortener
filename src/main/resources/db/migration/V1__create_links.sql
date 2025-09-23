CREATE TABLE links (
  slug           varchar(12) PRIMARY KEY,
  target_url     text NOT NULL,
  created_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
  expiration_date TIMESTAMPTZ NULL
);

CREATE INDEX IF NOT EXISTS idx_links_expiration_date ON links (expiration_date);
