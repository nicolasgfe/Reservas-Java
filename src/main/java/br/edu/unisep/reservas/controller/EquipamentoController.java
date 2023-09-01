package br.edu.unisep.reservas.controller;

import br.edu.unisep.reservas.exception.ResourceNotFoundException;
import br.edu.unisep.reservas.model.Equipamento;
import br.edu.unisep.reservas.repository.EquipamentoRepository;
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
public class EquipamentoController {

    @Autowired
    private EquipamentoRepository repository;

    @GetMapping("/equipamentos")
    public List<Equipamento> getAllEquipamentos(){
        return repository.findAll();
    }

    @GetMapping("/equipamentos/{id}")
    public ResponseEntity<Equipamento> getEquipamentoByID(@PathVariable(value = "id") Long equipamentoId)
            throws ResourceNotFoundException {
        Equipamento equipamento = repository.findById(equipamentoId).orElseThrow(()->
                new ResourceNotFoundException("Equipamento nao encontrado: " + equipamentoId));
        return ResponseEntity.ok().body(equipamento);
    }


    @PostMapping("/equipamentos")
    public Equipamento createEquipamento(@Validated @RequestBody Equipamento equipamento){
        equipamento.setCriadoEm(new Date());
        equipamento.setAlteradoEm(new Date());
        return repository.save(equipamento);
    }

    @PutMapping("/equipamentos/{id}")
    public ResponseEntity<Equipamento> updateEquipamento(@PathVariable(value = "id") Long equipamentoId,
                                           @Validated @RequestBody Equipamento detalhes)
    throws ResourceNotFoundException{
        Equipamento equipamento = repository.findById(equipamentoId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Equipamento nao encontrado: " + equipamentoId));
        equipamento.setNome(detalhes.getNome());
        equipamento.setTipo(detalhes.getTipo());
        equipamento.setAlteradoEm(new Date());
        final Equipamento updatedEquipamento = repository.save(equipamento);
        return ResponseEntity.ok(updatedEquipamento);
    }

    @DeleteMapping("/equipamentos/{id}")
    public Map<String, Boolean> deleteEquipamento(
            @PathVariable(value = "id") Long equipamentoId
    ) throws Exception{
        Equipamento equipamento = repository.findById(equipamentoId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Equipamento nao encontrado: " + equipamentoId));
        repository.delete(equipamento);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
