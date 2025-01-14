package med.voll.api.domain.consultas.validaciones.reserva;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consultas.DatosReservaConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorFueraHorarioConsultas implements ValidadorDeConsultas {

    public void validar(DatosReservaConsulta datos){
        var fechaConsulta = datos.fecha();
        var domingo = fechaConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var horarioAntesDeAperturaClinica = fechaConsulta.getHour() < 7;
        var horarioCierreClinica= fechaConsulta.getHour() > 18;

        if(domingo || horarioAntesDeAperturaClinica || horarioCierreClinica) {
            throw new ValidacionException("Horario seleccionado fuera del horario de atencion de la clinica: Agende un horario entre 7 a 18hs.");
        }
    }
}
