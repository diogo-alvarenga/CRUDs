CREATE TABLE IF NOT EXISTS USUARIO(
    id bigserial primary key,
    nome varchar(250) not null,
    email varchar(250) not null unique,
    senha varchar(250) not null
);