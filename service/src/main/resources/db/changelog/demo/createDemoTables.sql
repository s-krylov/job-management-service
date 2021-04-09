CREATE TABLE IF NOT EXISTS songs (
    id      BIGSERIAL,
    name    VARCHAR(200) NOT NULL,
    created TIMESTAMP NOT NULL DEFAULT now(),
    PRIMARY KEY (id)
);

INSERT INTO songs (name) VALUES ('Yellow submarine');
INSERT INTO songs (name) VALUES ('Hard day''s night');
INSERT INTO songs (name) VALUES ('Some Girls');
INSERT INTO songs (name) VALUES ('Paint it black');