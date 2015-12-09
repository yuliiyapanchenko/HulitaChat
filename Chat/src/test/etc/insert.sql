USE chat;

INSERT INTO roles (role) VALUES ('ROLE_USER');
INSERT INTO roles (role) VALUES ('ROLE_ADMIN');

INSERT INTO sign_in_providers (sign_in_provider) VALUE ('facebook');
INSERT INTO sign_in_providers (sign_in_provider) VALUE ('twitter');

INSERT INTO users (email, password, firstname, lastname, birtdate, enabled)
VALUES ('root@gmail.com', '$2a$10$YbYP0iwuJRFtlez1XV95wuOkCDJH5WBboo.rFqgwTbkNJh7LJe6gm', 'Юлия', 'Панченко',
        '1988-03-16', 1);

INSERT INTO authorities (role_id, user_id) VALUES (1, 1);
INSERT INTO authorities (role_id, user_id) VALUES (2, 1);