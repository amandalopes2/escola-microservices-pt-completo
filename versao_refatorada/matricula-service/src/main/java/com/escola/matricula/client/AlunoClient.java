
package com.escola.matricula.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Map;

@FeignClient(name = "alunoClient", url = "http://localhost:8081")
public interface AlunoClient {
    @GetMapping("/alunos/{id}")
    Map<String, Object> buscar(@PathVariable("id") String id);
}
