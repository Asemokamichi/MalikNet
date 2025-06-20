CREATE TABLE IF NOT EXISTS users
(
    id           SERIAL PRIMARY KEY,
    email        VARCHAR(255) UNIQUE NOT NULL,
    login        VARCHAR(100) UNIQUE NOT NULL,
    password     VARCHAR(255)        NOT NULL,
    first_name   VARCHAR(100),
    last_name    VARCHAR(100),
    middle_name  VARCHAR(100),
    is_confirmed BOOLEAN   DEFAULT FALSE,
    role         VARCHAR(50)         NOT NULL,
    created_at   TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP,
    update_at    TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS ads
(
    id                       SERIAL PRIMARY KEY,
    user_id                  INTEGER REFERENCES users (id) ON DELETE CASCADE,
    title                    VARCHAR(255)   NOT NULL,
    description              TEXT,
    min_price                NUMERIC(10, 2) NOT NULL,
    current_price            NUMERIC(10, 2),
    status                   VARCHAR(20),
    created_at               TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP,
    closed_at                TIMESTAMP,
    bidding_duration_minutes INTEGER
);


CREATE TABLE IF NOT EXISTS ad_images
(
    id        SERIAL PRIMARY KEY,
    ad_id     INTEGER REFERENCES ads (id) ON DELETE CASCADE,
    image_url TEXT NOT NULL
);


CREATE TABLE IF NOT EXISTS bids
(
    id         SERIAL PRIMARY KEY,
    ad_id      INTEGER REFERENCES ads (id) ON DELETE CASCADE,
    user_id    INTEGER REFERENCES users (id) ON DELETE CASCADE,
    amount     NUMERIC(10, 2) NOT NULL,
    created_at TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP
);