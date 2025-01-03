package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.DatosListadoMedico;
import med.voll.api.medico.DatosRegistroMedicos;
import med.voll.api.medico.IMedicoRepository;
import med.voll.api.medico.Medico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("medicos")
@Valid
public class MedicoController {

    @Autowired
    private IMedicoRepository medicoRepository;

    @PostMapping
    public void registerMedicos(@RequestBody DatosRegistroMedicos datosRegistroMedico){

       medicoRepository.save(new Medico(datosRegistroMedico));
    }


    @GetMapping
    public Page<DatosListadoMedico> listaMedicos(Pageable paginacion){
        return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
    }
}
