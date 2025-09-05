
package com.escola.matricula;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.*;

@SpringBootApplication
@RestController
public class MatriculaApplication {

    private final Map<String, Map<String, Object>> matriculas = new HashMap<>();

    @Autowired
    private RestTemplate restTemplate;

    public static void main(String[] args) {
        SpringApplication.run(MatriculaApplication.class, args);
    }

    @PostMapping("/matriculas")
    public ResponseEntity<?> matricular(@RequestParam String alunoId, @RequestParam String cursoId) {
        Map aluno = restTemplate.getForObject("http://localhost:8081/alunos/" + alunoId, Map.class);
        Map curso = restTemplate.getForObject("http://localhost:8082/cursos/" + cursoId, Map.class);

        if (aluno == null || curso == null) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Aluno ou Curso inexistente"));
        }

        String id = UUID.randomUUID().toString();
        Map<String, Object> registro = new HashMap<>();
        registro.put("id", id);
        registro.put("aluno", aluno);
        registro.put("curso", curso);
        matriculas.put(id, registro);
        return ResponseEntity.ok(registro);
    }

    @GetMapping("/matriculas/{id}")
    public ResponseEntity<?> buscar(@PathVariable String id) {
        Map<String, Object> m = matriculas.get(id);
        if (m == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(m);
    }
}

@Configuration
class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(); // sem timeout: intencional para demonstrar fragilidade
    }
}
