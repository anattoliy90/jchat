CREATE SCHEMA IF NOT EXISTS jchat;

CREATE SEQUENCE IF NOT EXISTS jchat.role_id_seq;
CREATE SEQUENCE IF NOT EXISTS jchat.user_id_seq;
CREATE SEQUENCE IF NOT EXISTS jchat.chat_id_seq;
CREATE SEQUENCE IF NOT EXISTS jchat.message_id_seq;

-- ENUMS

CREATE TABLE IF NOT EXISTS jchat.chat_types (
    id BIGINT NOT NULL,
    type VARCHAR NOT NULL,
    CONSTRAINT pkey_chat_types_id PRIMARY KEY (id)
);

-- MAIN TABLES

CREATE TABLE IF NOT EXISTS jchat.roles (
    id BIGINT NOT NULL,
    title VARCHAR NOT NULL UNIQUE,
    created TIMESTAMP NOT NULL DEFAULT now(),
    updated TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT pkey_roles_id PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS jchat.users (
    id BIGINT NOT NULL,
    first_name VARCHAR NOT NULL,
    last_name VARCHAR,
    avatar VARCHAR,
    email VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL,
    is_confirmed BOOLEAN NOT NULL,
    created TIMESTAMP NOT NULL DEFAULT now(),
    updated TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT pkey_user_id PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS jchat.chats (
    id BIGINT NOT NULL,
    title VARCHAR,
    image VARCHAR,
    created TIMESTAMP NOT NULL DEFAULT now(),
    updated TIMESTAMP NOT NULL DEFAULT now(),
    type_id BIGINT NOT NULL,
    CONSTRAINT pkey_chat_id PRIMARY KEY (id),
    CONSTRAINT fk_chat_type_id FOREIGN KEY (type_id) REFERENCES chat_types(id)
);

CREATE TABLE IF NOT EXISTS jchat.messages (
    id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    chat_id BIGINT NOT NULL,
    text VARCHAR NOT NULL,
    is_pinned BOOLEAN NOT NULL,
    created TIMESTAMP NOT NULL DEFAULT now(),
    updated TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT pkey_message_id PRIMARY KEY (id),
    CONSTRAINT fk_message_user_id FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_message_chat_id FOREIGN KEY (chat_id) REFERENCES chats(id)
);

-- ASSOCIATIVE TABLES

CREATE TABLE IF NOT EXISTS jchat.user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT pkey_user_roles PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user_id FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_user_roles_role_id FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE IF NOT EXISTS jchat.chat_users (
    chat_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pkey_chat_users PRIMARY KEY (chat_id, user_id),
    CONSTRAINT fk_chat_users_chat_id FOREIGN KEY (chat_id) REFERENCES chats(id),
    CONSTRAINT fk_chat_users_user_id FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS jchat.chat_admins (
    chat_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pkey_chat_admins PRIMARY KEY (chat_id, user_id),
    CONSTRAINT fk_chat_admins_chat_id FOREIGN KEY (chat_id) REFERENCES chats(id),
    CONSTRAINT fk_chat_admins_user_id FOREIGN KEY (user_id) REFERENCES users(id)
);
