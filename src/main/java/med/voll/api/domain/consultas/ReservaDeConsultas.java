package med.voll.api.domain.consultas;

import jakarta.validation.ValidationException;
import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.medico.IMedicoRepository;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservaDeConsultas {
    @Autowired
    private IMedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    public void reservar(DatosReservaConsulta datos){
        if (!pacienteRepository.existsById(datos.idPaciente())){
            throw new ValidacionException("No existe el paciente con el id: "+ datos.idPaciente() );
        }
        if (datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionException("No existe el medico con el id: "+ datos.idPaciente() );
        }

        var medico = elegirMedico(datos);
        var paciente = pacienteRepository.findById(datos.idPaciente()).get();


        var consulta = new Consultas(null,medico,paciente,datos.fecha());

        consultaRepository.save(consulta);
    }

    private Medico elegirMedico(DatosReservaConsulta datos) {
        if(datos.idMedico() != null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }

        if(datos.especialidad() ==null){
            throw new ValidationException("Es necesario elegir una eespcialidad cuando no se eleige un medico.");
        }

        return medicoRepository.elegirMedicoDisponiblePorEspecialidadYFecha(datos.especialidad(),datos.fecha());

    }
}
