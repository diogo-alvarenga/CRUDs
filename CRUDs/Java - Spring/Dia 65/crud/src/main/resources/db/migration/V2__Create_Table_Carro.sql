CREATE TABLE IF NOT EXISTS carro (
    id BIGSERIAL PRIMARY KEY,
    placa VARCHAR(50) NOT NULL UNIQUE,
    modelo VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    ano INTEGER NOT NULL,
    kmrodados REAL NOT NULL,
    usuario_id BIGINT NOT NULL,

    CONSTRAINT fk_carro_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuario(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_carro_usuario
ON carro(usuario_id);