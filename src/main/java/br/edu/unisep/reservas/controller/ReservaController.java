package br.edu.unisep.reservas.controller;

import br.edu.unisep.reservas.exception.ResourceNotFoundException;
import br.edu.unisep.reservas.model.Reserva;
import br.edu.unisep.reservas.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ReservaController {

    @Autowired
    private ReservaRepository repository;

    @GetMapping("/reservas")
    public List<Reserva> getAllReservas(){
        return repository.findAll();
    }

    @GetMapping("/reservas/{id}")
    public ResponseEntity<Reserva> getReservaByID(@PathVariable(value = "id") Long reservaId)
            throws ResourceNotFoundException {
        Reserva reserva = repository.findById(reservaId).orElseThrow(()->
                new ResourceNotFoundException("Reserva nao encontrado: " + reservaId));
        return ResponseEntity.ok().body(reserva);
    }

    @GetMapping("/reservas-dia/{dia}")
    public ResponseEntity<Reserva> getReservaByDay(@PathVariable(value = "dia") String dia)
            throws ResourceNotFoundException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Reserva reserva = repository.findByDataDevolucao(dia + " 00:00:00", dia + " 23:59:59");
        return ResponseEntity.ok().body(reserva);
    }

    @PostMapping("/reservas")
    public Reserva createReserva(@Validated @RequestBody Reserva reserva){
        reserva.setCriadoEm(new Date());
        reserva.setAlteradoEm(new Date());
        return repository.save(reserva);
    }

    @PutMapping("/reservas/{id}")
    public ResponseEntity<Reserva> updateReserva(@PathVariable(value = "id") Long reservaId,
                                           @Validated @RequestBody Reserva detalhes)
    throws ResourceNotFoundException{
        Reserva reserva = repository.findById(reservaId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Reserva nao encontrado: " + reservaId));
        reserva.setDataReserva(detalhes.getDataReserva());
        reserva.setDataDevolucao(detalhes.getDataDevolucao());
        reserva.setEquipamento(detalhes.getEquipamento());
        reserva.setStatus(detalhes.getStatus());
        reserva.setUsuario(detalhes.getUsuario());
        reserva.setAlteradoEm(new Date());
        final Reserva updatedReserva = repository.save(reserva);
        return ResponseEntity.ok(updatedReserva);
    }

    @DeleteMapping("/reservas/{id}")
    public Map<String, Boolean> deleteReserva(
            @PathVariable(value = "id") Long reservaId
    ) throws Exception{
        Reserva reserva = repository.findById(reservaId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Reserva nao encontrado: " + reservaId));
        repository.delete(reserva);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
