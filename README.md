
# Simulação de Microservices Resilientes — Sistema Escolar (Português Completo)

Contém duas versões:
- /versao_inicial/  — implementação propositalmente falha (chamadas diretas, God Service, RestTemplate sem timeout).
- /versao_refatorada/ — implementação com API Gateway, Feign, Resilience4j (Circuit Breaker, Bulkhead, TimeLimiter) e IoC.

Pré-requisitos: Java 17+, Maven 3.8+.
