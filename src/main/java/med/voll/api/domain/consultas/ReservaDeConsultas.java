package med.voll.api.domain.consultas;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consultas.validaciones.cancelamiento.ValidadorCanelamientoConsultas;
import med.voll.api.domain.consultas.validaciones.reserva.ValidadorDeConsultas;
import med.voll.api.domain.medico.IMedicoRepository;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaDeConsultas {
    @Autowired
    private IMedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private List<ValidadorDeConsultas> validaciones;

    @Autowired
    private List<ValidadorCanelamientoConsultas> validadorCancelacionConsultas;

    public DatosDetalleConsulta reservar(DatosReservaConsulta datos) {
        if (!pacienteRepository.existsById(datos.idPaciente())) {
            throw new ValidacionException("No existe el paciente con el id: " + datos.idPaciente());
        }
        if (datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())) {
            throw new ValidacionException("No existe el medico con el id: " + datos.idPaciente());
        }

        validaciones.forEach(v -> v.validar(datos));

        var medico = elegirMedico(datos);
        if (medico == null) {
            throw new ValidacionException("Ese medico elegido no esta disponible en ese horario.");
        }

        var paciente = pacienteRepository.findById(datos.idPaciente()).get();


        var consulta = new Consultas(null, medico, paciente, datos.fecha(), null);

        consultaRepository.save(consulta);
        return new DatosDetalleConsulta(consulta);
    }

    public Medico elegirMedico(DatosReservaConsulta datos) {
        System.out.println("Datos ; " +datos);
        if(datos.idMedico() != null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }

        if(datos.especialidad() ==null){
            throw new ValidacionException("Es necesario elegir una eespcialidad cuando no se eleige un medico.");
        }

        return medicoRepository.elegirMedicoDisponiblePorEspecialidadYFecha(datos.especialidad(),datos.fecha());

    }

    public void cancelar(DatosCancelacionReserva datos){
        if (!consultaRepository.existsById(datos.idConsulta())){
            throw new ValidacionException("Id de la consulta informado no existe.");
        }
        validadorCancelacionConsultas.forEach(c->c.validar((datos)));

        var consulta = consultaRepository.getReferenceById(datos.idConsulta());

        consulta.cancelar(datos.motivo());
    }
}
