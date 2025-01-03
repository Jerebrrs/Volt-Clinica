package med.voll.api.direccion;

import jakarta.validation.constraints.NotBlank;

public record DatosDireccion(
        @NotBlank
        String calle,

        @NotBlank
        String districto,

        @NotBlank
        String ciudad,
        @NotBlank
        String  numero,
        @NotBlank
        String complemento
) {
}
