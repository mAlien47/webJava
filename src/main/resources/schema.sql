
drop table if exists users;
drop table if exists expense;
drop table if exists wallet;

create table if not exists users (
    id identity,
    username varchar(20) not null,
    password varchar(100) not null,
    enabled bit not null
);
create table if not exists authorities (
    username varchar(20) not null,
    authority varchar(20) not null
);

create table if not exists wallet (
    id identity,
    create_date timestamp not null,
    type varchar(150) not null,

    username varchar(20),
    user_id bigint
);

create table if not exists expense (
    id identity ,
    create_date timestamp not null,
    name VARCHAR(50) not null,
    amount DECIMAL not null,
    expense_type VARCHAR(150) not null,

    wallet_id bigint,
    FOREIGN KEY (wallet_id) REFERENCES wallet
);
