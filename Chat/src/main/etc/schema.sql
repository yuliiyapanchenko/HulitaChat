USE chat;
CREATE TABLE users (
  id        INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
  email     VARCHAR(100) NOT NULL UNIQUE,
  password  VARCHAR(255),
  firstname VARCHAR(100) NOT NULL,
  lastname  VARCHAR(100) NOT NULL,
  birtdate  DATE,
  enabled   BOOLEAN      NOT NULL,
  creation_time     TIMESTAMP    NOT NULL,
  modification_time TIMESTAMP    NOT NULL
)
  DEFAULT CHARSET = utf8;

CREATE TABLE roles (
  id   INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
  role VARCHAR(100) NOT NULL UNIQUE,
  creation_time     TIMESTAMP    NOT NULL,
  modification_time TIMESTAMP    NOT NULL
);

CREATE TABLE sign_in_providers (
  id               INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
  sign_in_provider VARCHAR(100) NOT NULL UNIQUE,
  creation_time     TIMESTAMP    NOT NULL,
  modification_time TIMESTAMP    NOT NULL
);

CREATE TABLE authorities (
  id      INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  role_id INT NOT NULL,
  creation_time     TIMESTAMP    NOT NULL,
  modification_time TIMESTAMP    NOT NULL,
  CONSTRAINT fk_authorities_users FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT fk_authorities_roles FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE user_sig_in_provider (
  id                  INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  user_id             INT NOT NULL UNIQUE,
  sign_in_provider_id INT NOT NULL,
  creation_time     TIMESTAMP    NOT NULL,
  modification_time TIMESTAMP    NOT NULL,
  CONSTRAINT fk_user_providers FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT fk_providers FOREIGN KEY (sign_in_provider_id) REFERENCES sign_in_providers (id)
);

CREATE UNIQUE INDEX ix_role_user_id ON authorities (user_id, role_id);
CREATE UNIQUE INDEX ix_social_provider_user_id ON user_sig_in_provider (user_id, sign_in_provider_id);

CREATE TABLE conversations (
  id        INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
  parent_id INT,
  title     VARCHAR(50) NOT NULL,
  creator   INT,
  admin     INT,
  creation_time     TIMESTAMP    NOT NULL,
  modification_time TIMESTAMP    NOT NULL,
  CONSTRAINT fk_conversation_creator FOREIGN KEY (creator) REFERENCES users (id),
  CONSTRAINT fk_conversation_admin FOREIGN KEY (admin) REFERENCES users (id),
  CONSTRAINT fk_conversation_parent FOREIGN KEY (parent_id) REFERENCES conversations (id)
);

CREATE TABLE users_conversations (
  id              INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  id_user         INT NOT NULL,
  id_conversation INT NOT NULL,
  creation_time     TIMESTAMP    NOT NULL,
  modification_time TIMESTAMP    NOT NULL,
  CONSTRAINT fk_user FOREIGN KEY (id_user) REFERENCES users (id),
  CONSTRAINT fk_conversation FOREIGN KEY (id_conversation) REFERENCES conversations (id)
);

CREATE TABLE messages (
  id               INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
  id_conversation  INT          NOT NULL,
  id_user          INT          NOT NULL,
  message          VARCHAR(255) NOT NULL,
  creation_time     TIMESTAMP    NOT NULL,
  modification_time TIMESTAMP    NOT NULL,
  CONSTRAINT fk_originator FOREIGN KEY (id_user) REFERENCES users (id),
  CONSTRAINT fk_message_conversation FOREIGN KEY (id_conversation) REFERENCES conversations (id)
);

CREATE TABLE contacts (
  id         INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  id_user    INT NOT NULL,
  id_contact INT NOT NULL,
  creation_time     TIMESTAMP    NOT NULL,
  modification_time TIMESTAMP    NOT NULL,
  CONSTRAINT fk_contacts_user FOREIGN KEY (id_user) REFERENCES users (id),
  CONSTRAINT fk_contacts_contact FOREIGN KEY (id_contact) REFERENCES users (id)
);

CREATE UNIQUE INDEX ix_user_contact ON contacts (id_user, id_contact);

CREATE TABLE new_messages (
  id              INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  id_message      INT NOT NULL,
  id_user         INT NOT NULL,
  id_conversation INT NOT NULL,
  creation_time     TIMESTAMP    NOT NULL,
  modification_time TIMESTAMP    NOT NULL,
  CONSTRAINT fk_new_msg_id_msg FOREIGN KEY (id_message) REFERENCES messages (id),
  CONSTRAINT fk_new_msg_user FOREIGN KEY (id_user) REFERENCES users (id),
  CONSTRAINT fk_new_msg_conversation FOREIGN KEY (id_conversation) REFERENCES conversations (id)
);


CREATE TABLE UserConnection (
  userId         VARCHAR(255) NOT NULL,
  providerId     VARCHAR(255) NOT NULL,
  providerUserId VARCHAR(255),
  rank           INT          NOT NULL,
  displayName    VARCHAR(255),
  profileUrl     VARCHAR(512),
  imageUrl       VARCHAR(512),
  accessToken    VARCHAR(512) NOT NULL,
  secret         VARCHAR(512),
  refreshToken   VARCHAR(512),
  expireTime     BIGINT,
  PRIMARY KEY (userId, providerId, providerUserId)
);
CREATE UNIQUE INDEX UserConnectionRank ON UserConnection (userId, providerId, rank);
