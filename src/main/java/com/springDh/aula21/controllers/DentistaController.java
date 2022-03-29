package com.springDh.aula21.controllers;

import com.springDh.aula21.model.Dentista;
import com.springDh.aula21.services.DentistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dentistas")
public class DentistaController {

    @Autowired
    private final DentistaService dentistaService;

    public DentistaController(DentistaService dentistaService) {
        this.dentistaService = new DentistaService();
    }

    @PostMapping
    public ResponseEntity<Dentista> salvar(@RequestBody Dentista dentista){
        return ResponseEntity.ok(dentistaService.salvar(dentista));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dentista> buscarPorId(@PathVariable Integer id){
        return ResponseEntity.ok(dentistaService.buscar(id).orElse(null));
    }

    @GetMapping
    public ResponseEntity<List<Dentista>> buscarTodos(){
        return ResponseEntity.ok(dentistaService.buscarTodos());
    }

    @PutMapping
    public ResponseEntity<Dentista> atualizar(@RequestBody Dentista dentista){


        if (dentista.getId() != null && dentistaService.buscar(dentista.getId()).isPresent()){
           return ResponseEntity.ok(dentistaService.atualizar(dentista));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Integer id){

        if (dentistaService.buscar(id).isPresent()){
            dentistaService.excluir(id);
            return ResponseEntity.ok("Excluido com sucesso");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }
}
