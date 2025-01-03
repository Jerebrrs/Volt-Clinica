CREATE TABLE medicos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(15) NOT NULL,
    documento VARCHAR(20) NOT NULL,
    especialidad VARCHAR(50) NOT NULL,
    direccion JSONB NOT NULL
);
