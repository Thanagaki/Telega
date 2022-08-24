DROP TABLE IF EXISTS maps;
DROP TABLE IF EXISTS users;
CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id         BIGINT PRIMARY KEY DEFAULT nextval('global_seq'),
    chat_id    BIGINT UNIQUE            NOT NULL,
    userName   VARCHAR                   NOT NULL,
    bot_state  VARCHAR                   NOT NULL

);

CREATE TABLE maps
(
    id            BIGINT PRIMARY KEY DEFAULT nextval('global_seq'),
    mapName       VARCHAR NOT NULL

);
