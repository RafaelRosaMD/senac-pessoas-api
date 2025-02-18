package com.exercicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("pessoa")
public class PessoaController {

    @Autowired
    public PessoaRepository pessoaRepository;

    @GetMapping("/listar")
    ResponseEntity<List<Pessoa>> listar() {

        List<Pessoa> ListaPessoa = pessoaRepository.findAll();

        return ResponseEntity.ok().body(ListaPessoa);

    }

    @PostMapping("/nova")
    ResponseEntity<?> novaPessoa(
            @RequestBody Pessoa pessoa){

        if (pessoa.getNome().isEmpty() || pessoa.getNome() == null) {
            return ResponseEntity.badRequest().body("nome nao pode ser vazio");
        } else if (pessoa.getSobrenome().isEmpty() || pessoa.getSobrenome() == null) {
            return ResponseEntity.badRequest().body("sobrenome nao pode ser vazio");
        }

    }
}
