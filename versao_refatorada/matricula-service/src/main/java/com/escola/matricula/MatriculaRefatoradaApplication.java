
package com.escola.matricula;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

import com.escola.matricula.client.AlunoClient;
import com.escola.matricula.client.CursoClient;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.escola.matricula.client")
@RestController
@RequestMapping("/matriculas")
public class MatriculaRefatoradaApplication {

    private final Map<String, Map<String, Object>> matriculas = new HashMap<>();

    @Autowired
    private AlunoClient alunoClient;

    @Autowired
    private CursoClient cursoClient;

    public static void main(String[] args) {
        SpringApplication.run(MatriculaRefatoradaApplication.class, args);
    }

    @PostMapping
    @CircuitBreaker(name = "matriculaCb", fallbackMethod = "matriculaFallback")
    @Bulkhead(name = "matriculaBh", type = Bulkhead.Type.THREADPOOL)
    @TimeLimiter(name = "matriculaTl")
    public CompletableFuture<ResponseEntity<?>> matricular(@RequestParam String alunoId, @RequestParam String cursoId) {
        return CompletableFuture.supplyAsync(() -> {
            Map<String, Object> aluno = alunoClient.buscar(alunoId);
            Map<String, Object> curso = cursoClient.buscar(cursoId);

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
        });
    }

    public CompletableFuture<ResponseEntity<?>> matriculaFallback(String alunoId, String cursoId, Throwable ex) {
        Map<String, Object> fallback = Map.of(
            "mensagem", "Serviço em modo degradado: não foi possível completar a matrícula agora.",
            "alunoId", alunoId,
            "cursoId", cursoId,
            "causa", ex.getClass().getSimpleName()
        );
        return CompletableFuture.completedFuture(ResponseEntity.accepted().body(fallback));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable String id) {
        Map<String, Object> m = matriculas.get(id);
        if (m == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(m);
    }
}
