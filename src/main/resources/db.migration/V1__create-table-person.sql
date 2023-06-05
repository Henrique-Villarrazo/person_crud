create table person(

    id bigint not null auto_increment,
    name varchar(100) not null,
    email varchar(100) not null unique,
    cpf varchar(11) not null unique,
    rg varchar(9) not null unique,

    primary key(id)

);