package med.voll.api.domain.consultas;

import jakarta.validation.constraints.NotNull;

public record DatosCancelacionReserva(
        @NotNull
        Long idConsulta,
        @NotNull
        MotivoCancelamiento motivo
) {
}
