create table if not exists usuario(
	id bigint auto_increment primary key,
	nome varchar(250) not null,
	email varchar(250) not null,
	senha varchar(255) not null
);