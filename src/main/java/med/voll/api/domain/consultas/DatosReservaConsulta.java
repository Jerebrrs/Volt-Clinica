package med.voll.api.domain.consultas;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosReservaConsulta(
        @NotNull
        Long idMedico,
        @NotNull
        Long idPaciente,
        @Future
        LocalDateTime fecha
) {
}
