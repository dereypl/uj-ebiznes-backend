create TABLE PRODUCTS
(
    id          serial primary key,
    name        varchar(100),
    price       double precision,
    category_id integer
);

create TABLE CATEGORIES
(
    id   serial primary key,
    name varchar(100)
);


create TABLE ORDERS
(
    id      serial primary key,
    date    timestamp,
    user_id int references USERS (id)
);

create TABLE ORDER_PRODUCTS
(
    id         serial primary key,
    product_id integer,
    order_id   integer
);


ALTER TABLE PRODUCTS
    ADD CONSTRAINT fk_products_categories FOREIGN KEY (category_id) REFERENCES CATEGORIES (id);


ALTER TABLE ORDER_PRODUCTS
    ADD CONSTRAINT fk_order_products_products FOREIGN KEY (product_id) REFERENCES PRODUCTS (id);


ALTER TABLE ORDER_PRODUCTS
    ADD CONSTRAINT fk_order_products_order FOREIGN KEY (order_id) REFERENCES ORDERS (id);