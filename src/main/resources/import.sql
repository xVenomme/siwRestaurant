INSERT INTO credentials (id, username, password, user_id) VALUES (1, 'admin', '$2a$10$U.xhiVP.Du/xlv32XVsLi.ile25rBoFZ33Ey9dNkC1KjUXotKNvZu', null);
INSERT INTO users (id, username, name, surname, role, credentials_id) VALUES (1, 'admin', 'Admin', 'Admin', 'ADMIN', null);
UPDATE credentials SET user_id = 1 WHERE id = 1;
UPDATE users SET credentials_id = 1 WHERE id = 1;

INSERT INTO credentials (id, username, password, user_id) VALUES (2, 'user', '$2a$10$U.xhiVP.Du/xlv32XVsLi.ile25rBoFZ33Ey9dNkC1KjUXotKNvZu', null);
INSERT INTO users (id, username, name, surname, role, credentials_id) VALUES (2, 'user', 'user', 'user', 'USER', null);
UPDATE credentials SET user_id = 2 WHERE id = 2;
UPDATE users SET credentials_id = 2 WHERE id = 2;


