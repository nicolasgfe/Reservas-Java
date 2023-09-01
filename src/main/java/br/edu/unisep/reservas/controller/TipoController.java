package br.edu.unisep.reservas.controller;

import br.edu.unisep.reservas.exception.ResourceNotFoundException;
import br.edu.unisep.reservas.model.Tipo;
import br.edu.unisep.reservas.repository.TipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class TipoController {

    @Autowired
    private TipoRepository repository;

    @GetMapping("/tipos")
    public List<Tipo> getAllTipos(){
        return repository.findAll();
    }

    @GetMapping("/tipos/{id}")
    public ResponseEntity<Tipo> getTipoByID(@PathVariable(value = "id") Long tipoId)
            throws ResourceNotFoundException {
        Tipo tipo = repository.findById(tipoId).orElseThrow(()->
                new ResourceNotFoundException("Tipo nao encontrado: " + tipoId));
        return ResponseEntity.ok().body(tipo);
    }


    @PostMapping("/tipos")
    public Tipo createTipo(@Validated @RequestBody Tipo tipo){
        tipo.setCriadoEm(new Date());
        tipo.setAlteradoEm(new Date());
        return repository.save(tipo);
    }

    @PutMapping("/tipos/{id}")
    public ResponseEntity<Tipo> updateTipo(@PathVariable(value = "id") Long tipoId,
                                           @Validated @RequestBody Tipo detalhes)
    throws ResourceNotFoundException{
        Tipo tipo = repository.findById(tipoId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Tipo nao encontrado: " + tipoId));
        tipo.setDescricao(detalhes.getDescricao());

        tipo.setAlteradoEm(new Date());
        final Tipo updatedTipo = repository.save(tipo);
        return ResponseEntity.ok(updatedTipo);
    }

    @DeleteMapping("/tipos/{id}")
    public Map<String, Boolean> deleteTipo(
            @PathVariable(value = "id") Long tipoId
    ) throws Exception{
        Tipo tipo = repository.findById(tipoId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Tipo nao encontrado: " + tipoId));
        repository.delete(tipo);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
