CREATE TABLE tickets (
    id SERIAL PRIMARY KEY,
    session_id INT NOT NULL REFERENCES sessions(id),
    row_ses INT NOT NULL,
    cell INT NOT NULL,
    user_id INT NOT NULL REFERENCES users(id),
    CONSTRAINT unique_ticket UNIQUE (session_id, row_ses, cell)
);