
# Simulação de Microservices Resilientes — Sistema Escolar

Contém duas versões:
- /versao_inicial/  — implementação propositalmente falha (chamadas diretas, God Service, RestTemplate sem timeout).
- /versao_refatorada/ — implementação com API Gateway, Feign, Resilience4j (Circuit Breaker, Bulkhead, TimeLimiter) e IoC.

Pré-requisitos: Java 17+, Maven 3.8+.

Instruções de execução: 
Abra 4 terminais diferentes e execute:

```bash
# Subir Aluno Service
cd versao_refatorada/aluno-service
mvn spring-boot:run

# Subir Curso Service
cd versao_refatorada/curso-service
mvn spring-boot:run

# Subir Matrícula Service
cd versao_refatorada/matricula-service
mvn spring-boot:run

# Subir API Gateway
cd versao_refatorada/api-gateway
mvn spring-boot:run
