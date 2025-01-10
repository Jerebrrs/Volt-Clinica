CREATE TABLE medicos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    documento VARCHAR(20) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    activo BOOlEAN DEFAULT(TRUE),
    direccion JSONB NOT NULL,
);



