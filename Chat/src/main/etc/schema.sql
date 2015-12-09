USE chat;
CREATE TABLE users (
  id        INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
  email     VARCHAR(100) NOT NULL UNIQUE,
  password  VARCHAR(255),
  firstname VARCHAR(100) NOT NULL,
  lastname  VARCHAR(100) NOT NULL,
  birtdate  DATE,
  enabled   BOOLEAN      NOT NULL
) DEFAULT CHARSET=utf8;

CREATE TABLE roles (
  id   INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
  role VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE sign_in_providers (
  id               INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
  sign_in_provider VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE authorities (
  id      INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  role_id INT NOT NULL,
  CONSTRAINT fk_authorities_users FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT fk_authorities_roles FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE user_sig_in_provider (
  id                  INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  user_id             INT NOT NULL UNIQUE,
  sign_in_provider_id INT NOT NULL,
  CONSTRAINT fk_user_providers FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT fk_providers FOREIGN KEY (sign_in_provider_id) REFERENCES sign_in_providers (id)
);

CREATE UNIQUE INDEX ix_role_user_id ON authorities (user_id, role_id);
CREATE UNIQUE INDEX ix_social_provider_user_id ON user_sig_in_provider (user_id, sign_in_provider_id);

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
  id              INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  id_user         INT NOT NULL,
  id_conversation INT NOT NULL,
  CONSTRAINT fk_user FOREIGN KEY (id_user) REFERENCES users (id),
  CONSTRAINT fk_conversation FOREIGN KEY (id_conversation) REFERENCES conversations (id)
);

CREATE TABLE messages (
  id              INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
  id_conversation INT          NOT NULL,
  id_user         INT          NOT NULL,
  message         VARCHAR(255) NOT NULL,
  CONSTRAINT fk_originator FOREIGN KEY (id_user) REFERENCES users (id),
  CONSTRAINT fk_message_conversation FOREIGN KEY (id_conversation) REFERENCES conversations (id)
);


create table UserConnection (userId varchar(255) not null,
                             providerId varchar(255) not null,
                             providerUserId varchar(255),
                             rank int not null,
                             displayName varchar(255),
                             profileUrl varchar(512),
                             imageUrl varchar(512),
                             accessToken varchar(512) not null,
                             secret varchar(512),
                             refreshToken varchar(512),
                             expireTime bigint,
  primary key (userId, providerId, providerUserId));
create unique index UserConnectionRank on UserConnection(userId, providerId, rank);
