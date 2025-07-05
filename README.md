# Relatório dos testes de cargas - SLA -  S.K.A.T.E.H.O.L.D.E.R.S.
## Nome do Serviço 1: Registrar atividade (possivelmente criar sessão)
 https://github.com/frederico-spiguel/s.k.a.t.e.h.o.l.d.e.r.s./blob/main/relatorioservico1.html
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
