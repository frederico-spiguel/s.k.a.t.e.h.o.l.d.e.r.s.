# Relatório dos testes de cargas - SLA -  S.K.A.T.E.H.O.L.D.E.R.S.
### Nome do Serviço 1: Registrar atividade (possivelmente criar sessão)
Endpoint: POST /atividades
Tipo de operações: Leitura e Escrita.
Leitura: O serviço acessa o contexto de segurança para obter o Usuario logado e realiza uma busca (SELECT) na base de dados para verificar a existência de uma Sesh para o dia corrente.
Escrita: O serviço realiza uma inserção (INSERT) na tabela atividades. Potencialmente, também realiza um INSERT na tabela sesh caso seja a primeira atividade do dia para o usuário.
Arquivos envolvidos:
backend/src/main/java/com/skateholders/skateholders/controllers/AtividadeController.java - 
backend/src/main/java/com/skateholders/skateholders/services/AtividadeService.java - 
backend/src/main/java/com/skateholders/skateholders/models/Atividade.java - 
backend/src/main/java/com/skateholders/skateholders/models/Sesh.java - 
backend/src/main/java/com/skateholders/skateholders/models/Usuario.java -
backend/src/main/java/com/skateholders/skateholders/config/SecurityConfig.java - 
backend/src/main/java/com/skateholders/skateholders/security/SecurityFilter.java - 
Arquivos com o código fonte de medição do SLA:
load-testing/teste-registro-atividade.js - 
Data da medição: 04/07/2025
Descrição das configurações:
Sistema: Aplicação back-end baseada em Spring Boot 3, rodando sobre Java 17 localmente (localhost:8080).
Banco de Dados: PostgreSQL (versão X.X), rodando localmente na mesma máquina.
Executor do Teste: Ferramenta de teste de carga k6 (versão X.X).
Máquina: Os testes e a aplicação foram executados na mesma máquina: -> <- Este setup elimina a latência de rede, permitindo focar nos gargalos da aplicação e do banco de dados.
Testes de carga (SLA): Foram realizados dois testes para observar a evolução das métricas: um teste de linha de base (carga leve) e um teste de estresse (carga extrema).
Teste 1: Linha de Base (Carga Leve)
Latência (tempo de resposta médio): 6.44 ms
Vazão (quantidade média de requisições por segundo): ~107 reqs/s
Concorrência: O sistema suportou uma carga de 200 usuários virtuais (VUs) com 100% de sucesso.
Observação: A performance se mostrou excelente e estável.
Teste 2: Teste de Estresse (Carga Extrema)
Latência (tempo de resposta médio): ~198 ms (para as requisições que falharam).
Vazão (quantidade média de requisições por segundo): Pico de ~6108 reqs/s.
Concorrência: O sistema foi submetido a 2000 usuários virtuais (VUs) e apresentou uma taxa de falha de 100%, indicando que o limite de capacidade foi ultrapassado.
LEVANTAMENTO DE HIPÓTESES dos potenciais gargalos do sistema que influenciam esta funcionalidade
Os testes de carga revelaram que a aplicação é extremamente performática para esta operação sob carga normal e pesada, mas atinge um ponto de ruptura abrupto sob carga extrema. A análise dos resultados permite levantar as seguintes hipóteses de gargalos, que foram comprovadas pelo teste de estresse:
Gargalo Primário - Esgotamento do Pool de Conexões do Banco de Dados: A causa mais provável para a falha de 100% das requisições no teste de estresse é a saturação do pool de conexões com o banco de dados (HikariCP). Com um limite padrão de 10 conexões, a vazão de mais de 6000 req/s esgotou instantaneamente todas as conexões disponíveis, fazendo com que o serviço não conseguisse mais se comunicar com o banco para realizar as inserções.
Gargalo Secundário - Esgotamento do Pool de Threads do Servidor Web: Concomitantemente, o pool de threads do servidor web embutido (Tomcat), que por padrão lida com 200 requisições simultâneas, também foi saturado. Isso impede que o servidor aceite novas conexões HTTP, resultando em falhas antes mesmo da lógica da aplicação ser executada.
Ausência de Gargalo na Lógica da Aplicação: É crucial notar que a latência se manteve relativamente baixa mesmo no teste de estresse (~198ms), indicando que as requisições que conseguiam ser processadas eram rápidas. Isso sugere que a lógica de negócio no AtividadeService e a query de INSERT não são os gargalos; o sistema falha por esgotamento de recursos de infraestrutura (conexões e threads), não por lentidão no código.
