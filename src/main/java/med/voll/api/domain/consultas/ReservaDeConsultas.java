package med.voll.api.domain.consultas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservaDeConsultas {
    @Autowired
    private ConsultaRepository consultaRepository;

    public void reservar(DatosReservaConsulta datos){

    }
}
