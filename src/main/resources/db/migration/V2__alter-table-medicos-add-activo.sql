DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'medicos' AND column_name = 'activo') THEN
        ALTER TABLE medicos ADD COLUMN activo BOOLEAN DEFAULT TRUE;
    END IF;
END $$;
