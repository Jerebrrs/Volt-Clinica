package med.voll.api.domain.consultas;

import med.voll.api.domain.ValidacionException;

import java.time.LocalDateTime;

public record DatosDetalleConsulta (
        Long id,
        Long idMedico,
        Long idPaciente,
        LocalDateTime fecha
){
    public DatosDetalleConsulta(Consultas consulta) {
        this(consulta.getId(),consulta.getMedico().getId(),consulta.getPaciente().getId(),consulta.getFecha());
    }
}
