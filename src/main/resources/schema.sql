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

create table vouchers
(
    voucher_id  BINARY(16) PRIMARY KEY,
    value       int          NOT NULL,
    type        VARCHAR(50)  NOT NULL,
    description VARCHAR(200) NOT NULL,
    created_at  datetime(6)  NOT NULL,
    updated_at  datetime(6) DEFAULT NULL
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

insert into products values(UUID_TO_BIN(UUID()), '허쉬 춰퀄릿',2000,30,'초콜릿',now(), now());
insert into products values(UUID_TO_BIN(UUID()), '페렐레 로쉐에',3000,20,'초콜릿',now(), now());
insert into products values(UUID_TO_BIN(UUID()), '가놔 쵸퀄렛',1000,30,'초콜릿',now(), now());

insert into  vouchers values(UUID_TO_BIN(UUID()), 10000,'FIXED_AMOUNT_VOUCHER','10000원 할인',now(), now());
insert into  vouchers values(UUID_TO_BIN(UUID()), 20000, 'FIXED_AMOUNT_VOUCHER','20000원 할인',now(), now());
insert into  vouchers values(UUID_TO_BIN(UUID()), 30,'PERCENT_DISCOUNT_VOUCHER','30% 할인',now(), now());


