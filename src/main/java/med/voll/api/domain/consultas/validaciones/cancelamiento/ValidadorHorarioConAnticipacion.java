package med.voll.api.domain.consultas.validaciones.cancelamiento;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consultas.ConsultaRepository;
import med.voll.api.domain.consultas.DatosCancelacionReserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidadorConsultaConAnticipacionReserva")
public class ValidadorHorarioConAnticipacion implements ValidadorCanelamientoConsultas{
    @Autowired
    private ConsultaRepository repository;

    @Override
    public void validar(DatosCancelacionReserva datos){
        var consulta = repository.getReferenceById(datos.idConsulta());
        var ahora = LocalDateTime.now();
        var diferenciaEnHoras = Duration.between(ahora, consulta.getFecha()).toHours();

        if(diferenciaEnHoras < 24){
            throw new ValidacionException("La consulta solo puede cancelarce con una anticipacion de 24hs.");
        }
    }
}
