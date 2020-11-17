CREATE SEQUENCE w_account_id_seq INCREMENT 1 START 1;

CREATE SEQUENCE w_user_id_seq INCREMENT 1 START 1;

CREATE TABLE w_account (
    id int8 NOT NULL DEFAULT nextval('w_account_id_seq'),
    owner_id int8,
    phone_model varchar(255) NOT NULL,
    phone_imei varchar(255),
    phone_number varchar(255),
    first_name varchar(255) NOT NULL,
    last_name varchar(255) NOT NULL,
    birth_date date,
    email varchar(255),
    em_password varchar(255),
    secret_answers varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE w_user (
    id int8 NOT NULL DEFAULT nextval('w_user_id_seq'),
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    email varchar(255),
    active BOOLEAN DEFAULT TRUE,
    PRIMARY KEY (id)
);

CREATE TABLE w_user_role (
    user_id int8 NOT NULL,
    roles varchar(55)
);

ALTER TABLE w_user
    ADD CONSTRAINT w_user_unique_name
    UNIQUE (username);

ALTER TABLE w_account
    ADD CONSTRAINT w_account_user_fk FOREIGN KEY (owner_id)
        REFERENCES w_user (id)
        ON UPDATE CASCADE
        ON DELETE SET NULL;

ALTER TABLE w_user_role
    ADD CONSTRAINT w_user_role_fk FOREIGN KEY (user_id)
        REFERENCES w_user (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE;
