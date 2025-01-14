package med.voll.api.domain.consultas.validaciones.reserva;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consultas.DatosReservaConsulta;
import med.voll.api.domain.medico.IMedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorConsultasMedicoInactivo implements ValidadorDeConsultas {
    @Autowired
    private IMedicoRepository repository;

    public void validar(DatosReservaConsulta datos) {
        if(datos.idMedico() == null){
            return;
        }
        var medicoEstaActivo = repository.findActivoById(datos.idMedico());

        if(!medicoEstaActivo){
            throw new ValidacionException("Consulta No puede ser reservada con medico exluido.");
        }
    }
}
