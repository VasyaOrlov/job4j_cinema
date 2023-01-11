CREATE TABLE ticket (
    id SERIAL PRIMARY KEY,
    session_id INT NOT NULL REFERENCES sessions(id),
    row INT NOT NULL,
    cell INT NOT NULL,
    CONSTRAINT unique_ticket UNIQUE (session_id, row, cell)
);