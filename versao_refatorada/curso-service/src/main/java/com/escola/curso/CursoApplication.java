
package com.escola.curso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.*;

@SpringBootApplication
@RestController
@RequestMapping("/cursos")
public class CursoApplication {

    private final Map<String, Map<String, String>> armazenamento = new HashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(CursoApplication.class, args);
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Map<String, String> curso) {
        armazenamento.put(curso.get("id"), curso);
        return ResponseEntity.ok(curso);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable String id) {
        Map<String, String> c = armazenamento.get(id);
        if (c == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(c);
    }
}
