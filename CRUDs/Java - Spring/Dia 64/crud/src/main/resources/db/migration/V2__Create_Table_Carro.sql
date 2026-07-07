CREATE TABLE IF NOT EXISTS CARRO(
    id bigserial primary key,
    modelo varchar(100) not null,
    marca varchar(100) not null,
    ano integer not null,
    email varchar(100) not null,
    placa varchar(50) not null unique,
    usuario_id bigint not null,

    constraint fk_carro_usuario
        foreign key (usuario_id)
        references usuario(id)
        on delete cascade
);