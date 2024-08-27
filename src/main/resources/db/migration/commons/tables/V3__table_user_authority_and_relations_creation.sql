
drop table if exists users;

CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255)
);

drop table if exists authority;

CREATE TABLE authority (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           roles VARCHAR(255) NOT NULL
);

drop table if exists user_authority;

CREATE TABLE user_authority (
                                user_id BIGINT NOT NULL,
                                authority_id BIGINT NOT NULL,
                                PRIMARY KEY (user_id, authority_id),
                                FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                                FOREIGN KEY (authority_id) REFERENCES authority(id) ON DELETE CASCADE
);