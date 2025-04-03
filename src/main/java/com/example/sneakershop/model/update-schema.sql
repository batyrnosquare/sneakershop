
CREATE TABLE item
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name  VARCHAR(255),
    brand VARCHAR(255),
    color VARCHAR(255),
    price DOUBLE PRECISION,
    CONSTRAINT pk_item PRIMARY KEY (id)
);

CREATE TABLE "order"
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    user_id    BIGINT,
    status     VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    CONSTRAINT pk_order PRIMARY KEY (id)
);

CREATE TABLE shoes
(
    id       BIGINT NOT NULL,
    material VARCHAR(255),
    type     VARCHAR(255),
    sex      VARCHAR(255),
    CONSTRAINT pk_shoes PRIMARY KEY (id)
);

CREATE TABLE sizes
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    item_id  BIGINT,
    size     INTEGER,
    quantity INTEGER,
    CONSTRAINT pk_sizes PRIMARY KEY (id)
);

CREATE TABLE socks
(
    id       BIGINT NOT NULL,
    material VARCHAR(255),
    CONSTRAINT pk_socks PRIMARY KEY (id)
);

CREATE TABLE users
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    username VARCHAR(255),
    password VARCHAR(255),
    email    VARCHAR(255),
    role     VARCHAR(255),
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);

ALTER TABLE shoes
    ADD CONSTRAINT FK_SHOES_ON_ID FOREIGN KEY (id) REFERENCES item (id);

ALTER TABLE sizes
    ADD CONSTRAINT FK_SIZES_ON_ITEM FOREIGN KEY (item_id) REFERENCES item (id);

ALTER TABLE socks
    ADD CONSTRAINT FK_SOCKS_ON_ID FOREIGN KEY (id) REFERENCES item (id);