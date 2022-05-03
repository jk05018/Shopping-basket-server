create table products
(
    product_id      BINARY(16) PRIMARY KEY,
    product_name    VARCHAR(20) NOT NULL,
    price           bigint      NOT NULL,
    remain_quantity int         NOT NULL,
    description     VARCHAR(500) DEFAULT NULL,
    created_at      datetime(6) NOT NULL,
    updated_at      datetime(6)  DEFAULT NULL
);


create table orders
(
    order_id   BINARY(16) PRIMARY KEY,
    voucher_id BINARY(16),
    email      VARCHAR(50)  NOT NULL,
    address    VARCHAR(200) NOT NULL,
    postcode   VARCHAR(200) NOT NULL,
    created_at datetime(6)  NOT NULL,
    updated_at datetime(6) DEFAULT NULL,
    CONSTRAINT fk_orders_to_vouchers FOREIGN KEY (voucher_id) REFERENCES vouchers (voucher_id)
);

create table order_items
(
    seq        bigint     NOT NULL PRIMARY KEY AUTO_INCREMENT,
    order_id   BINARY(16) NOT NULL,
    product_id BINARY(16) NOT NULL,
    price      bigint     NOT NULL,
    quantity   int        NOT NULL,
    INDEX (order_id),
    CONSTRAINT fk_order_items_to_order FOREIGN KEY (order_id) REFERENCES orders (order_id) ON DELETE CASCADE,
    CONSTRAINT fk_order_items_to_product FOREIGN KEY (product_id) REFERENCES products (product_id)
);

create table vouchers
(
    voucher_id  BINARY(16) PRIMARY KEY,
    value       int          NOT NULL,
    type        VARCHAR(50)  NOT NULL,
    description VARCHAR(200) NOT NULL,
    created_at  datetime(6)  NOT NULL,
    updated_at  datetime(6) DEFAULT NULL
);

