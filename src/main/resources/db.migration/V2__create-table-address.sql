create table adress(

    id bigint not null auto_increment,
    street varchar(100) not null,
    district varchar(100) not null unique,
    cep varchar(8) not null unique,
    number varchar(50) not null unique,
    complement varchar(500),
    city varchar(100),
    uf varchar(2),

    primary key (id),
    foreign key (person_id) references person (id)

);