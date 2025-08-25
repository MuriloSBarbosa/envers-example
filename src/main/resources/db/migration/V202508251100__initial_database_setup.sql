-- director table
CREATE TABLE director (
    id varchar(36) PRIMARY KEY,
    name VARCHAR(255)
);

-- book table
CREATE TABLE book (
    id varchar(36) PRIMARY KEY,
    title VARCHAR(255),
    genre VARCHAR(255),
    director_id VARCHAR(36),
    release_date DATE,
    rating DOUBLE PRECISION,
    description TEXT,
    foreign key (director_id) references director(id)
);

-- revision info table
CREATE TABLE revinfo (
    rev BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    revtstmp BIGINT,
    modified_by VARCHAR(255),
    ip_address VARCHAR(45)
);
-- audit table
CREATE TABLE book_AUD (
    id varchar(36) NOT NULL,
    rev BIGINT NOT NULL,
    revtype SMALLINT,
    title VARCHAR(255),
    genre VARCHAR(255),
    director_id VARCHAR(36),
    release_date DATE,
    rating DOUBLE PRECISION,
    description TEXT,
    PRIMARY KEY (id, rev),
    CONSTRAINT fk_book_aud_rev FOREIGN KEY (rev) REFERENCES revinfo (rev)
);

CREATE TABLE director_AUD (
    id varchar(36) NOT NULL,
    rev BIGINT NOT NULL,
    revtype SMALLINT,
    name VARCHAR(255),
    PRIMARY KEY (id, rev),
    CONSTRAINT fk_director_aud_rev FOREIGN KEY (rev) REFERENCES revinfo (rev)
);