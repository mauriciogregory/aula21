package com.springDh.aula21.controllers;

import com.springDh.aula21.dao.impl.UsuarioDaoMySql;
import com.springDh.aula21.model.Usuario;
import com.springDh.aula21.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UserController {


    private UsuarioService usuarioService;

    public UserController() {
        this.usuarioService = new UsuarioService(new UsuarioDaoMySql());
    }

    @PostMapping
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario){

        return ResponseEntity.ok(usuarioService.salvar(usuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Integer id){
        return ResponseEntity.ok(usuarioService.buscar(id).orElse(null));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> buscarTodos(){
        return ResponseEntity.ok(usuarioService.buscarTodos());
    }

    @PutMapping
    public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario){


        if (usuario.getId() != null && usuarioService.buscar(usuario.getId()).isPresent()){
            return ResponseEntity.ok(usuarioService.atualizar(usuario));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Integer id){

        if (usuarioService.buscar(id).isPresent()){
            usuarioService.excluir(id);
            return ResponseEntity.ok("Excluido com sucesso");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }



}
