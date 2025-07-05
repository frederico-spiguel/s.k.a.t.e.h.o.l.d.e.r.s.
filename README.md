# Relatório dos testes de cargas - SLA -  S.K.A.T.E.H.O.L.D.E.R.S.
## Nome do Serviço 1: Registrar atividade (possivelmente criar sessão)
 ## Gráfico e tabelas -> https://github.com/frederico-spiguel/s.k.a.t.e.h.o.l.d.e.r.s./blob/main/relatorioservico1.html
## Endpoint: POST /atividades

## Tipo de operações: Leitura e Escrita

### Leitura
- O serviço acessa o contexto de segurança para obter o **usuário logado**.
- Realiza uma busca (`SELECT`) no banco de dados para verificar a existência de uma `Sesh` para o dia corrente.

### Escrita
- O serviço realiza um `INSERT` na tabela `atividades`.
- Caso seja a **primeira atividade do dia** para o usuário, também realiza um `INSERT` na tabela `sesh`.

---

## Arquivos envolvidos

- `backend/src/main/java/com/skateholders/skateholders/controllers/AtividadeController.java`
- `backend/src/main/java/com/skateholders/skateholders/services/AtividadeService.java`
- `backend/src/main/java/com/skateholders/skateholders/models/Atividade.java`
- `backend/src/main/java/com/skateholders/skateholders/models/Sesh.java`
- `backend/src/main/java/com/skateholders/skateholders/models/Usuario.java`
- `backend/src/main/java/com/skateholders/skateholders/config/SecurityConfig.java`
- `backend/src/main/java/com/skateholders/skateholders/security/SecurityFilter.java`
- `load-testing/teste-registro-atividade.js`

---

## Medição de desempenho (SLA)

**Data da medição:** 04/07/2025

**Configurações do ambiente:**
- **Sistema:** Aplicação back-end em Spring Boot 3, rodando com Java 17 localmente (`localhost:8080`).
- **Banco de Dados:** PostgreSQL (versão X.X), rodando localmente na mesma máquina.
- **Executor do Teste:** Ferramenta de teste de carga k6 (versão X.X).
- **Máquina:** Notebook com Windows 11, processador Core i7-10750H, 16GB RAM DDR4.
- **Observação:** A aplicação, o banco e o teste rodaram na mesma máquina, eliminando a latência de rede.

---

### Teste 1: Linha de Base (Carga Leve)

- **Latência média:** 6.44 ms
- **Vazão:** ~107 requisições por segundo
- **Concorrência:** 200 usuários virtuais (VUs)
- **Taxa de sucesso:** 100%
- **Conclusão:** Performance excelente e estável

### Teste 2: Estresse (Carga Extrema)

- **Latência média das requisições que falharam:** ~198 ms
- **Vazão máxima observada:** ~6108 requisições por segundo
- **Concorrência:** 2000 usuários virtuais (VUs)
- **Taxa de falhas:** 100%
- **Conclusão:** O sistema excedeu sua capacidade máxima de processamento

---

## Levantamento de hipóteses dos gargalos

### Gargalo Primário: Esgotamento do Pool de Conexões com o Banco de Dados (HikariCP)
- O limite padrão de 10 conexões foi rapidamente esgotado com a carga extrema.
- Isso impossibilitou o serviço de executar queries no banco de dados.

### Gargalo Secundário: Esgotamento do Pool de Threads do Servidor Web (Tomcat)
- O Tomcat, por padrão, suporta até 200 threads simultâneas.
- Com a carga extrema, novas conexões HTTP foram recusadas antes mesmo da lógica da aplicação ser executada.

### O que **não** foi gargalo:
- A lógica da aplicação (`AtividadeService`, comandos SQL) manteve uma latência baixa (~198ms).
- Indica que a aplicação é eficiente e o problema está nos **recursos de infraestrutura**, não no código.


# Serviço: Buscar Detalhes da Sessão

## Endpoint
`GET /seshs/por-data?data={data}`

## Tipo de Operações
- **Leitura apenas**

### Descrição da Leitura
- Realiza uma busca complexa (`SELECT`) que envolve múltiplas tabelas: `Sesh`, `Atividade`, `Trick`.
- Compõe a resposta com todos os detalhes de uma sessão específica e suas atividades.

---

## Arquivos envolvidos

- `backend/src/main/java/com/skateholders/skateholders/controllers/SeshController.java`
- `backend/src/main/java/com/skateholders/skateholders/services/SeshService.java`
- `backend/src/main/java/com/skateholders/skateholders/repositories/SeshRepository.java`
- `backend/src/main/java/com/skateholders/skateholders/models/Sesh.java`
- `backend/src/main/java/com/skateholders/skateholders/models/Atividade.java`
- `backend/src/main/java/com/skateholders/skateholders/config/SecurityConfig.java`
- `load-testing/teste-busca-sessao.js`

---

## Medição de Desempenho (SLA)

**Data da medição:** 05/07/2025

**Configurações do Ambiente:**
- **Sistema:** Spring Boot 3 com Java 17 (localhost:8080)
- **Banco de Dados:** PostgreSQL 15, rodando localmente
- **Executor do Teste:** Ferramenta de carga `k6`
- **Máquina:** Notebook com Windows 11, Intel Core i7, 16GB RAM

---

### Teste: Carga Moderada

- **Latência média:** 15.71 segundos (15718.88 ms)
- **Vazão:** ~2.35 requisições por segundo
- **Concorrência:** 50 usuários virtuais (VUs)
- **Taxa de sucesso:** 99.6%
- **Resultado:** Teste falhou o threshold de latência de 2s. Embora a taxa de sucesso seja alta, a resposta é lenta demais para ser aceitável.

---

## Levantamento de Hipóteses de Gargalos

###  Gargalo Principal — Problema de "N+1 Select" (comprovado)
- Para cada requisição:
  - 1 query busca a sessão (`Sesh`)
  - N queries adicionais buscam cada `Atividade` relacionada
- Para sessões com ~50 atividades, o sistema executa ~51 queries por requisição

### Impacto
- Cada requisição ocupa uma **thread de servidor** e uma **conexão de banco** por 15–30 segundos
- Isso afeta diretamente a **vazão e escalabilidade** do sistema
- Sob essa condição, o sistema **não escala horizontalmente**

### Gargalo de Código (solucionável)
- O problema está na camada de acesso a dados (JPA/Repository)
- Pode ser resolvido com uso de `JOIN FETCH` ou `@EntityGraph`, eliminando o padrão N+1 e otimizando a consulta

---

## Conclusão

- O serviço é funcional, mas sofre de sérios problemas de performance causados por um padrão ineficiente de acesso a dados.
- **Prioridade de correção:** alta, pois afeta diretamente a experiência do usuário e a capacidade do sistema de atender múltiplos usuários simultaneamente.
