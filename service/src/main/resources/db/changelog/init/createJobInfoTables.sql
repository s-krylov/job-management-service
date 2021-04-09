CREATE TABLE IF NOT EXISTS job_info (
    id      BIGSERIAL,
    name    VARCHAR(200) NOT NULL,
    status  VARCHAR(30) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT name_unique UNIQUE (name)
);

CREATE UNIQUE INDEX IF NOT EXISTS job_info_name_idx ON job_info USING btree (name);