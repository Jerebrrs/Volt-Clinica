package med.voll.api.medico;

public record DatosListadoMedico(
        String nombre,
        String email,
        String documento,
        String especialidad
) {
    public DatosListadoMedico(Medico medico) {
        this(
                medico.getNombre(),
                medico.getEspecialidad().toString(),
                medico.getDocumento(),
                medico.getEmail()
        );
    }
}
