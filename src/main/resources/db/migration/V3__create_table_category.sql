create table category
(
    id          bigint          not null auto_increment,
    name        varchar(255)    not null,
    description varchar(2000)   ,
    primary key (id)
);

alter table auction
    add column category_id bigint not null,
    add constraint auction_category_fk foreign key (category_id) references category (id);