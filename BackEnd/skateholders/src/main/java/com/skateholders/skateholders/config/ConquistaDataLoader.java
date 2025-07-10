package com.skateholders.skateholders.config;

import com.skateholders.skateholders.mongoDocs.ConquistaDoc;
import com.skateholders.skateholders.mongoReps.ConquistaDocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Povoa o banco de dados MongoDB com o catálogo de conquistas
 * na primeira inicialização da aplicação.
 */
@Component
public class ConquistaDataLoader implements CommandLineRunner {

    @Autowired
    private ConquistaDocRepository conquistaRepository;

    @Override
    public void run(String... args) throws Exception {
        if (conquistaRepository.count() == 0) {
            System.out.println(">>> [ConquistaDataLoader] Catálogo de conquistas vazio. Povoando o MongoDB...");

            List<ConquistaDoc> conquistas = criarConquistasMock();
            conquistaRepository.saveAll(conquistas);

            System.out.println(">>> [ConquistaDataLoader] Catálogo com " + conquistas.size() + " conquistas povoado com sucesso!");
        } else {
            System.out.println(">>> [ConquistaDataLoader] Catálogo de conquistas já existe. Nenhuma ação necessária.");
        }
    }

    private List<ConquistaDoc> criarConquistasMock() {
        List<ConquistaDoc> lista = new ArrayList<>();

        // --- Conquistas de Nível Iniciante (Acertos Baixos) ---
        lista.add(criarConquista("Primeiro Ollie", "Acerte seu primeiro Ollie.", "ACERTOS_TRICK", 10, "/emblemas/primeiro_ollie.png", Map.of("trickId", 15L, "quantidade", 1)));
        lista.add(criarConquista("Primeiro Flip", "Acerte seu primeiro Kickflip.", "ACERTOS_TRICK", 10, "/emblemas/primeiro_flip.png", Map.of("trickId", 8L, "quantidade", 1)));
        lista.add(criarConquista("No Chão de Fábrica", "Registre 10 atividades no total.", "TOTAL_ATIVIDADES", 10, "/emblemas/dez_atividades.png", Map.of("quantidade", 10)));
        lista.add(criarConquista("Semana Produtiva", "Registre 50 atividades no total.", "TOTAL_ATIVIDADES", 25, "/emblemas/cinquenta_atividades.png", Map.of("quantidade", 50)));
        lista.add(criarConquista("Aprendiz de Shove-it", "Acerte 10 Pop Shove-its.", "ACERTOS_TRICK", 20, "/emblemas/aprendiz_shoveit.png", Map.of("trickId", 10L, "quantidade", 10))); // Supondo que Pop Shove-it é ID 10

        // --- Conquistas de Nível Intermediário ---
        lista.add(criarConquista("Rei do Flat", "Acerte 50 Kickflips.", "ACERTOS_TRICK", 50, "/emblemas/rei_flat.png", Map.of("trickId", 8L, "quantidade", 50)));
        lista.add(criarConquista("Amante do Heelflip", "Acerte 50 Heelflips.", "ACERTOS_TRICK", 50, "/emblemas/amante_heelflip.png", Map.of("trickId", 9L, "quantidade", 50))); // Supondo que Heelflip é ID 9
        lista.add(criarConquista("Voo Consistente", "Acerte 100 Ollies.", "ACERTOS_TRICK", 75, "/emblemas/voo_consistente.png", Map.of("trickId", 15L, "quantidade", 100)));
        lista.add(criarConquista("Centenário", "Registre 100 atividades no total.", "TOTAL_ATIVIDADES", 75, "/emblemas/centenario.png", Map.of("quantidade", 100)));
        lista.add(criarConquista("Giro de 180", "Acerte 25 BS 180 Ollies.", "ACERTOS_TRICK", 40, "/emblemas/giro_180.png", Map.of("trickId", 20L, "quantidade", 25))); // Supondo que BS 180 é ID 20

        // --- Conquistas de Nível Avançado ---
        lista.add(criarConquista("Mestre dos Flips", "Acerte 250 Kickflips.", "ACERTOS_TRICK", 150, "/emblemas/mestre_flips.png", Map.of("trickId", 8L, "quantidade", 250)));
        lista.add(criarConquista("Double Trouble", "Acerte 50 Double Kickflips.", "ACERTOS_TRICK", 200, "/emblemas/double_trouble.png", Map.of("trickId", 17L, "quantidade", 50))); // Supondo que Double Flip é ID 17
        lista.add(criarConquista("Dono da Pista", "Registre 500 atividades no total.", "TOTAL_ATIVIDADES", 300, "/emblemas/dono_pista.png", Map.of("quantidade", 500)));
        lista.add(criarConquista("Cirurgião do Varial", "Acerte 100 Varial Heelflips.", "ACERTOS_TRICK", 250, "/emblemas/cirurgiao_varial.png", Map.of("trickId", 25L, "quantidade", 100))); // Supondo que Varial Heel é ID 25
        lista.add(criarConquista("Lenda do Asfalto", "Registre 1000 atividades no total.", "TOTAL_ATIVIDADES", 500, "/emblemas/lenda_asfalto.png", Map.of("quantidade", 1000)));

        // --- Conquistas "Criativas" (com requisitos simples por enquanto) ---
        lista.add(criarConquista("Rei do Switch", "Domine a arte de andar na base trocada.", "ACERTOS_TRICK", 100, "/emblemas/rei_switch.png", Map.of("trickId", 30L, "quantidade", 50))); // Requisito bobo: 50x uma trick de switch qualquer
        lista.add(criarConquista("Arquiteto das Linhas", "Conecte manobras com fluidez.", "TOTAL_ATIVIDADES", 100, "/emblemas/arquiteto_linhas.png", Map.of("quantidade", 300))); // Requisito bobo: 300 atividades
        lista.add(criarConquista("Amigo da Cera", "Mostre maestria em grinds e slides.", "ACERTOS_TRICK", 150, "/emblemas/amigo_cera.png", Map.of("trickId", 35L, "quantidade", 50))); // Requisito bobo: 50x uma trick de corrimão
        lista.add(criarConquista("Verticalmente Inclinado", "Explore as transições e o skate vertical.", "ACERTOS_TRICK", 150, "/emblemas/vertical.png", Map.of("trickId", 36L, "quantidade", 20))); // Requisito bobo: 20x uma trick de transição
        lista.add(criarConquista("Colecionador de Manobras", "Desbloqueie todas as outras conquistas.", "TOTAL_ATIVIDADES", 1000, "/emblemas/colecionador.png", Map.of("quantidade", 9999))); // Requisito bobo (e quase impossível por enquanto)

        return lista;
    }

    /**
     * Método de ajuda para criar um objeto ConquistaDoc de forma mais limpa.
     */
    private ConquistaDoc criarConquista(String nome, String descricao, String tipo, int pontos, String imagemUrl, Map<String, Object> requisitos) {
        ConquistaDoc doc = new ConquistaDoc();
        doc.setNome(nome);
        doc.setDescricao(descricao);
        doc.setTipo(tipo);
        doc.setPontos(pontos);
        doc.setImagemUrl(imagemUrl);
        doc.setRequisitos(requisitos);
        return doc;
    }
}