package com.exercicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin
@RequestMapping("pessoa")
public class PessoaController {

    @Autowired
    public PessoaRepository pessoaRepository;

    @GetMapping("/listar")
    ResponseEntity<List<Pessoa>> listar(){

        List<Pessoa> listaPessoa = pessoaRepository.findAll();

        return ResponseEntity.ok().body(listaPessoa);

    }

    @PostMapping("/nova")
    ResponseEntity<?> novaPessoa(
            @RequestBody Pessoa pessoa
    ){

        if (pessoa.getNome().isEmpty() || pessoa.getNome() == null){
            return ResponseEntity.badRequest().body("Nome não pode ser vazio");
        } else if (pessoa.getSobrenome().isEmpty() || pessoa.getSobrenome() == null)
        {
            return ResponseEntity.badRequest().body("Sobrenome não pode ser vazio");
        } else if (pessoa.getAltura() <= 0){
            return ResponseEntity.badRequest().body("Digite uma altura válida.");
        } else if (pessoa.getIdade() <=0 ){
            return ResponseEntity.badRequest().body("Digite uma idade válida");
        }

        Pessoa pessoaSalva = pessoaRepository.save(pessoa);
        return ResponseEntity.ok().body(pessoaSalva);
    }


    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarPessoa(
            @RequestBody Pessoa pessoa,
            @PathVariable long id
    ){
        pessoaRepository.findById(id)
                .map(record ->{

                    if (!pessoa.getNome().isEmpty() || pessoa.getNome() != null){
                        record.setNome(pessoa.getNome());
                    } else if (!pessoa.getSobrenome().isEmpty() || pessoa.getSobrenome() != null)
                    {
                        record.setSobrenome(pessoa.getSobrenome());
                    } else if (pessoa.getAltura() > 0){
                        record.setAltura(pessoa.getAltura());
                    } else if (pessoa.getIdade() > 0 ){
                        record.setAltura(pessoa.getAltura());
                    }

                    Pessoa pessoaEditada = pessoaRepository.save(record);
                    return ResponseEntity.ok().body(pessoaEditada);

                }).orElse(ResponseEntity.notFound().build());
        return null;
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluirPessoa(
            @PathVariable long id
    ){  return pessoaRepository.findById(id)

            .map(record-> {pessoaRepository.deleteById(id);
                return ResponseEntity.ok().body("Excluido com sucesso");
            })
            .orElse(ResponseEntity.notFound().build());
    }
}