package med.voll.api.domain.medico;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;

@Getter()
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "Medico")
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

    private Boolean activo;

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
        this.activo= true;
    }

    public void actualizarDatos(DatosUpdateMedico datosUpdateMedico) {
        if(datosUpdateMedico.nombre() != null){
            this.nombre= datosUpdateMedico.nombre();
        }
        if(datosUpdateMedico.documento() != null){
            this.documento= datosUpdateMedico.documento();
        }
        if(datosUpdateMedico.direccion() != null){
            this.direccion=direccion.actualizarDatos(datosUpdateMedico.direccion());
        }
    }

    public void desactivarMedico() {
        this.activo = false;
    }
}
