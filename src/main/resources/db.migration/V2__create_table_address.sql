create table address(
    id uuid not null,
    street varchar(100) not null,
    district varchar(100) not null unique,
    cep varchar(8) not null unique,
    number varchar(50) not null unique,
    complement varchar(500),
    city varchar(100),
    uf varchar(2),
    person_id uuid,

    primary key (id),
    foreign key (person_id) references person (id)
);