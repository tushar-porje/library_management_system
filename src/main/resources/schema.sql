CREATE TABLE IF NOT EXISTS book_table (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    genre VARCHAR(255),
    year_Published INTEGER,
    available BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS user_table (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS issued_book_table (
    id SERIAL PRIMARY KEY,
    book_id BIGINT,
    user_id BIGINT,
    issue_date DATE,
    return_date DATE,
    FOREIGN KEY (book_id) REFERENCES book_table(id) ON DELETE SET NULL,
    FOREIGN KEY (user_id) REFERENCES user_table(id) ON DELETE SET NULL
);
