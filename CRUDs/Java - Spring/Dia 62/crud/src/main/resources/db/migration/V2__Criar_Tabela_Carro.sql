CREATE TABLE carro (
    id BIGSERIAL PRIMARY KEY,
    modelo VARCHAR(100) NOT NULL,
    marca VARCHAR(100) NOT NULL,
    ano INTEGER NOT NULL,
    email VARCHAR(100) NOT NULL,
    placa VARCHAR(50) NOT NULL UNIQUE,
    usuario_id BIGINT NOT NULL,

    CONSTRAINT fk_carro_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuario(id)
        ON DELETE CASCADE
);