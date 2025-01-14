package med.voll.api.domain.consultas.validaciones.reserva;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consultas.ConsultaRepository;
import med.voll.api.domain.consultas.DatosReservaConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ValidadorMedicoConOtraConsultaEnElMismoDia implements ValidadorDeConsultas {
  @Autowired
    private ConsultaRepository repository;

    public void validar(DatosReservaConsulta datos){
        var medicoTieneConsultaEnElMimsoHorario= repository.existsByMedicoIdAndFechaAndMotivoCancelamientoIsNull(datos.idMedico(),datos.fecha());
            if (medicoTieneConsultaEnElMimsoHorario) {
                throw new ValidacionException("Medico ya tiene otra consulta en esa misma fecha y hora.");
            }
    }
}
