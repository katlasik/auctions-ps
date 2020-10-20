create table auction
(
    id       bigint       not null auto_increment,
    owner_id bigint not null,
    title    varchar(255) not null,
    description     varchar(2000) not null,
    price decimal(10, 2) not null,
    primary key (id)
);

alter table auction
    add constraint auction_fk foreign key (owner_id) references user (id);