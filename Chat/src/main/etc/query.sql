USE chat;
CREATE TABLE users (
  id        INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
  username  VARCHAR(50)  NOT NULL,
  password  VARCHAR(100) NOT NULL,
  firstname VARCHAR(50)  NOT NULL,
  lastname  VARCHAR(50)  NOT NULL,
  birtdate  DATETIME     NOT NULL,
  enabled   BOOLEAN      NOT NULL
);

CREATE TABLE roles (
  id   INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
  role VARCHAR(50) NOT NULL
);

CREATE TABLE authorities (
  id      INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  role_id INT NOT NULL,
  CONSTRAINT fk_authorities_users FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT fk_authorities_roles FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE UNIQUE INDEX ix_role_user_id ON authorities (user_id, role_id);

CREATE TABLE conversations (
  id        INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  parent_id INT,
  title     VARCHAR(50),
  creator   INT,
  admin     INT,
  CONSTRAINT fk_conversation_creator FOREIGN KEY (creator) REFERENCES users (id),
  CONSTRAINT fk_conversation_admin FOREIGN KEY (admin) REFERENCES users (id),
  CONSTRAINT fk_conversation_parent FOREIGN KEY (parent_id) REFERENCES conversations (id)
);

CREATE TABLE users_conversations (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  id_user         INT NOT NULL,
  id_conversation INT NOT NULL,
  CONSTRAINT fk_user FOREIGN KEY (id_user) REFERENCES users (id),
  CONSTRAINT fk_conversation FOREIGN KEY (id_conversation) REFERENCES conversations (id)
);

CREATE TABLE messages (
  id              INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
  id_conversation INT          NOT NULL,
  id_user         INT          NOT NULL,
  msg_time        TIMESTAMP    NOT NULL,
  message         VARCHAR(255) NOT NULL,
  CONSTRAINT fk_originator FOREIGN KEY (id_user) REFERENCES users (id),
  CONSTRAINT fk_message_conversation FOREIGN KEY (id_conversation) REFERENCES conversations (id)
);



