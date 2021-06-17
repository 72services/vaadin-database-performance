-- Sequences

create sequence customer_seq increment by 50;
create sequence employee_seq increment by 50;
create sequence order_item_seq increment by 50;
create sequence product_seq increment by 50;
create sequence purchase_order_seq increment by 50;

-- Tables

create table customer
(
    id        int not null
        constraint pk_customer primary key,
    firstname varchar(255),
    lastname  varchar(255)
);

create table employee
(
    id            integer not null
        constraint pk_employee primary key,
    date_of_birth date,
    email         varchar(255),
    first_name    varchar(255),
    important     boolean not null,
    last_name     varchar(255),
    occupation    varchar(255),
    phone         varchar(255),
    supervisor_id integer
        constraint fk_employee_supervisor references employee
);


create table product
(
    id    int              not null
        constraint pk_product primary key,
    name  varchar(255),
    price double precision not null
);


create table purchase_order
(
    id          int not null
        constraint pk_purchase_order primary key,
    order_date  date,
    customer_id int
        constraint fk_purchase_order_customer references customer
);


create table order_item
(
    id         int     not null
        constraint order_item_pkey
            primary key,
    quantity   integer not null,
    product_id int
        constraint fk_order_item_product references product,
    order_id   int     not null
        constraint fk_order_item_purchase_order references purchase_order
);
