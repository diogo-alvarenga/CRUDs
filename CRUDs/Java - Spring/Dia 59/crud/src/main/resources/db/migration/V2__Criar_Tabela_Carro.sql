CREATE TABLE carro (
    id BIGSERIAL PRIMARY KEY,
    modelo VARCHAR(255) NOT NULL,
    fabricante VARCHAR(255) NOT NULL,
    ano INTEGER NOT NULL,
    kmRodados DOUBLE PRECISION NOT NULL,
    usuario_id BIGINT NOT NULL,
    CONSTRAINT fk_carro_usuario
    FOREIGN KEY (usuario_id)
    REFERENCES usuario (id)
);