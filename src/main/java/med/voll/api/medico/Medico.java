package med.voll.api.medico;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.direccion.Direccion;

@Getter()
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "medicos")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true)
    private String email;

    private String telefono;

    private String documento;

    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;

    @Embedded
    private Direccion direccion;

    public Medico(DatosRegistroMedicos datosRegistroMedico) {
        this.nombre= datosRegistroMedico.nombre();
        this.email= datosRegistroMedico.email();
        this.especialidad= datosRegistroMedico.especialidad();
        this.documento= datosRegistroMedico.documento();
        this.telefono= datosRegistroMedico.telefono();
        this.direccion=new Direccion(datosRegistroMedico.direccion()) ;
    }
}
