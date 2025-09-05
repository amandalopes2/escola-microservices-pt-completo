
# Versão Inicial - (com falhas propositalmente)
Serviços e portas:
- aluno-service: 8081
- curso-service: 8082
- matricula-service: 8083

A matrícula faz chamadas diretas via RestTemplate sem timeout, demonstrando anti-patterns.
