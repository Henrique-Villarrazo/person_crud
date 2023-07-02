create table address(
    id uuid not null,
    street varchar(100) not null,
    district varchar(100) not null,
    cep varchar(8) not null,
    number varchar(50) not null,
    complement varchar(500),
    city varchar(100),
    uf varchar(2),
    person_id uuid,

    primary key (id),
    foreign key (person_id) references person (id)
);