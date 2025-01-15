package med.voll.api.domain.medico;

import jakarta.persistence.EntityManager;
import med.voll.api.domain.consultas.Consultas;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.direccion.Direccion;
import med.voll.api.domain.paciente.DatosRegistroPaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class IMedicoRepositoryTest {

    @Autowired
    private IMedicoRepository medicoRepository;
    @Autowired
    private EntityManager en;

    @Test
    @DisplayName("Deberia devolver null cuando el medico buscado existe pero no esta disponible en esa fecha.")
    void elegirMedicoDisponiblePorEspecialidadYFecha() {
        var lunesSigienteAlas6 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);
         var medico = registerMedico("medico","jere@gamil.com","39836919",Especialidad.CARDIOLOGIA);
         var paciente=registroPaciente("pacinete4","oacu@gmail.com","3531684");
         registrarConsulta(medico,paciente,lunesSigienteAlas6);

         var medicoLibre = medicoRepository.elegirMedicoDisponiblePorEspecialidadYFecha(Especialidad.CARDIOLOGIA,lunesSigienteAlas6);

         assertThat(medicoLibre).isNull();

    }

    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha){
        en.persist(new Consultas(null,medico,paciente,fecha,null));
    }
    private Medico registerMedico(String nombre, String email, String documento, Especialidad especialidad){
        var medico = new Medico(datosMedicos(nombre, email, documento, especialidad));
        en.persist(medico);
        return medico;
    }
    private Paciente registroPaciente(String nombre, String email,String documento){
        var paciente = new Paciente((datosRegistroPaciente(nombre, email, documento)));
        en.persist(paciente);
        return paciente;
    }
    private DatosRegistroMedicos datosMedicos(String nombre,String documento, String email, Especialidad especialidad){
        return new DatosRegistroMedicos(
                nombre,
                email,
                "3437445403",
                documento,
                especialidad,
                datosDireccion()
        );
    }

    private DatosRegistroPaciente datosRegistroPaciente(String nombre,String email,String documento){
        return new DatosRegistroPaciente(
                nombre,
                email,
                "3437445403",
                documento,
                datosDireccion()
        );
    }
    private DatosDireccion datosDireccion(){
        return new DatosDireccion(
                "calle x",
                "Distrito y",
                "parana",
                "3437445403",
                "2"
        );
    }
}