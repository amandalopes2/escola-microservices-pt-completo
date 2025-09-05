
package com.escola.matricula.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Map;

@FeignClient(name = "cursoClient", url = "http://localhost:8082")
public interface CursoClient {
    @GetMapping("/cursos/{id}")
    Map<String, Object> buscar(@PathVariable("id") String id);
}
