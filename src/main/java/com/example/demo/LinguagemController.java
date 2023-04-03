package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;



@RestController // para aceitar GET POST etc.
public class LinguagemController {

    @Autowired
    private LinguagemRepository repositorio; 

    @GetMapping("/hello-world")
    public String testeHW() {
        return "Hello World";
    }
    
    @GetMapping("/top-lang")
    public List<Linguagem> obterLinguagens() {
        List<Linguagem> linguagens = repositorio.findAll();
        return linguagens;
    }

    @PostMapping("/top-lang")
    public Linguagem cadastrarLinguagem(@RequestBody Linguagem linguagem) {
        Linguagem linguagemSalva = repositorio.save(linguagem);
        return linguagemSalva;
    }

    @GetMapping("/top-lang/{id}")
    public Linguagem obterLinguagemID(@PathVariable String id) {
        return repositorio.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)); // Throw 404
    }

    @PutMapping("/top-lang/{id}")
    public Linguagem atualizarLinguagem(@PathVariable String id, @RequestBody Linguagem linguagem) { 
        if (!repositorio.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }  // validar ID, pra poder atualizar mesmo SEM ID NO JSON, apenas no url

        linguagem.setId(id); // setar ID na linguagem caso não existe, e assim regista com ID

        Linguagem linguagemSalva = repositorio.save(linguagem);
        return linguagemSalva;
    }

    @DeleteMapping("/top-lang/{id}")
    public void excluirLinguagem(@PathVariable String id) {
        repositorio.deleteById(id);
    }

}
