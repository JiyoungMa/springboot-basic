drop database springbasic;
create database springbasic;

use springbasic;

CREATE TABLE customers
(
    customer_id BINARY(16) PRIMARY KEY,
    name varchar(20) NOT NULL,
    email varchar(50) NOT NULL UNIQUE,
    last_login_at datetime(6) DEFAULT NULL,
    created_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT unq_user_email UNIQUE (email)
);

CREATE TABLE vouchers
(
    voucher_id BINARY(16) PRIMARY KEY,
    amount INTEGER NOT NULL,
    created_at datetime(6) NOT NULL,
    voucher_type varchar(20) NOT NULL
);

CREATE TABLE wallet
(
    wallet_id BINARY(16) PRIMARY KEY,
    customer_id BINARY(16),
    voucher_id BINARY(16),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (voucher_id) REFERENCES vouchers(voucher_id)
);