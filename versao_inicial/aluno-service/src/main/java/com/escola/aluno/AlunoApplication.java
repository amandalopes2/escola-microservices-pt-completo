
package com.escola.aluno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.*;

@SpringBootApplication
@RestController
@RequestMapping("/alunos")
public class AlunoApplication {

    private final Map<String, Map<String, String>> armazenamento = new HashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(AlunoApplication.class, args);
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Map<String, String> aluno) {
        armazenamento.put(aluno.get("id"), aluno);
        return ResponseEntity.ok(aluno);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable String id) {
        Map<String, String> a = armazenamento.get(id);
        if (a == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(a);
    }
}
