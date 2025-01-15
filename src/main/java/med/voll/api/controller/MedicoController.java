package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    @Autowired
    private IMedicoRepository medicoRepository;

    @PostMapping
    public ResponseEntity<DatosResponseMedico> registerMedicos(@RequestBody @Valid DatosRegistroMedicos datosRegistroMedico,
                                                               UriComponentsBuilder uriComponentsBuilder){

       Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));

       DatosResponseMedico datosResponseMedico = new DatosResponseMedico(medico.getId(),medico.getNombre(),medico.getEmail(),medico.getTelefono(),medico.getEspecialidad().toString(),
               new DatosDireccion(medico.getDireccion().getCalle(),medico.getDireccion().getDistricto(),
                       medico.getDireccion().getCiudad(),medico.getDireccion().getNumero(),
                       medico.getDireccion().getComplemento()));
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
       return ResponseEntity.created(url).body(datosResponseMedico);
    }


    @GetMapping
    public ResponseEntity<Page<DatosListadoMedico>> listaMedicos(Pageable paginacion){
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosResponseMedico> updateMedico(@RequestBody @Valid DatosUpdateMedico datosUpdateMedico){
        Medico medico = medicoRepository.getReferenceById(datosUpdateMedico.id());
        medico.actualizarDatos(datosUpdateMedico);
    return ResponseEntity.ok(new DatosResponseMedico(
            medico.getId(),medico.getNombre(),medico.getEmail(),medico.getTelefono(),medico.getEspecialidad().toString(),
            new DatosDireccion(medico.getDireccion().getCalle(),medico.getDireccion().getDistricto(),
                    medico.getDireccion().getCiudad(),medico.getDireccion().getNumero(),
                    medico.getDireccion().getComplemento())));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Medico> deleteMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosResponseMedico> medicoFindById(@PathVariable Long id){

        Medico medico = medicoRepository.getReferenceById(id);

        var datosMedicos= new DatosResponseMedico(medico.getId(),medico.getNombre(),medico.getEmail(),medico.getTelefono(),medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(),medico.getDireccion().getDistricto(),
                        medico.getDireccion().getCiudad(),medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()));
        return ResponseEntity.ok(datosMedicos);
    }
}
