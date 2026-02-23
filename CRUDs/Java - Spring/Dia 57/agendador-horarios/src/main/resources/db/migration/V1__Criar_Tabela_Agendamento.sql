create table if not exists agendamento(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    servico VARCHAR(255),
    profissional VARCHAR(255),
    data_hora_agendamento TIMESTAMP,
    cliente VARCHAR(255),
    telefone_cliente VARCHAR(50),
    data_insercao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);