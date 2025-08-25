-- book table
CREATE TABLE book (
    id varchar(36) PRIMARY KEY,
    title VARCHAR(255),
    genre VARCHAR(255),
    director VARCHAR(255),
    release_date DATE,
    rating DOUBLE PRECISION,
    description TEXT
);


-- revision info table
CREATE TABLE revinfo (
    rev BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    revtstmp BIGINT,
    modified_by VARCHAR(255)
);
-- audit table
CREATE TABLE book_AUD (
    id varchar(36) NOT NULL,
    rev BIGINT NOT NULL,
    revtype SMALLINT,
    title VARCHAR(255),
    genre VARCHAR(255),
    director VARCHAR(255),
    release_date DATE,
    rating DOUBLE PRECISION,
    description TEXT,
    PRIMARY KEY (id, rev),
    CONSTRAINT fk_book_aud_rev FOREIGN KEY (rev) REFERENCES revinfo (rev)
);
