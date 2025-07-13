package com.skateholders.skateholders.config;

import com.skateholders.skateholders.mongoDocs.TrickTutorial;
import com.skateholders.skateholders.mongoReps.TrickTutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrickTutorialDataLoader implements CommandLineRunner {

    @Autowired
    private TrickTutorialRepository tutorialRepository; // Use o nome correto do seu repositório

    @Override
    public void run(String... args) throws Exception {
        // Limpa a coleção para garantir que estamos usando a nova estrutura
        tutorialRepository.deleteAll();

        if (tutorialRepository.count() == 0) {
            System.out.println(">>> [TrickTutorialDataLoader] Povoando o catálogo de tutoriais com a nova estrutura...");

            List<TrickTutorial> tutoriais = new ArrayList<>();

            // --- Novos tutoriais com descrição ---

            tutoriais.add(criarTutorial(
                    "Ollie - O Salto Fundamental",
                    "O Ollie é o truque mais importante do skate. É a base para quase todas as outras manobras de street e transição. Dominá-lo abre um mundo de possibilidades.",
                    "https://www.youtube.com/embed/Vas94p3aR1c", // Exemplo de URL de embed
                    List.of(
                            "Posicione o pé de trás na ponta do tail e o da frente no meio do shape.",
                            "Agache e prepare para o impulso.",
                            "Bata o tail no chão com força (o 'pop') e, quase ao mesmo tempo, pule.",
                            "Deslize o pé da frente em direção ao nose para nivelar o skate no ar.",
                            "Aterrisse com os joelhos flexionados para absorver o impacto."
                    )
            ));

            tutoriais.add(criarTutorial(
                    "Kickflip - O Giro Essencial",
                    "O Kickflip é o primeiro 'flip' que a maioria dos skatistas aprende. Consiste em fazer o skate girar em seu eixo longitudinal com um chute dos dedos do pé da frente.",
                    "https://www.youtube.com/embed/YOFy1w362oY",
                    List.of(
                            "A posição dos pés é similar à do Ollie, mas com o pé da frente um pouco mais para trás e inclinado.",
                            "Bata o tail e pule como em um Ollie.",
                            "O segredo: chute com o tornozelo do pé da frente, na diagonal, para fora do skate na altura dos parafusos do nose.",
                            "Veja o skate girar e espere para 'pescá-lo' com os pés no ar.",
                            "Caia sobre os parafusos para ter mais estabilidade."
                    )
            ));

            tutoriais.add(criarTutorial(
                    "Andando de Switch",
                    "Andar de switch é andar com a sua base não-natural. Se você é regular, switch é andar como goofy. É um dos pilares para um skate completo e técnico.",
                    "https://www.youtube.com/embed/5g3e4s9g9eY",
                    List.of(
                            "Comece apenas remando e se equilibrando na base trocada para ganhar confiança.",
                            "Pratique curvas para os dois lados. Seu corpo vai se sentir estranho.",
                            "Quando estiver confortável, tente dar um Ollie. A coordenação será um novo desafio.",
                            "A chave é a paciência e tratar o aprendizado como se estivesse começando do zero."
                    )
            ));

            tutoriais.add(criarTutorial(
                    "Pulando Escadas",
                    "Levar suas manobras para escadas é um grande passo. Requer confiança, velocidade e um Ollie sólido. A sensação de acertar uma manobra em um gap é incomparável.",
                    "https://www.youtube.com/embed/t_265yO_i24",
                    List.of(
                            "A base de tudo é um Ollie alto e consistente. Treine muito isso no chão.",
                            "Comece com obstáculos baixos, como um ou dois degraus, para entender o tempo e a velocidade.",
                            "Aproxime-se da escada com velocidade suficiente para passar o vão, mas com controle.",
                            "Dê o 'pop' do Ollie um pouco antes do primeiro degrau.",
                            "Mantenha o corpo centrado sobre o skate e prepare-se para absorver o impacto na aterrisagem."
                    )
            ));

            tutorialRepository.saveAll(tutoriais);
            System.out.println(">>> [TrickTutorialDataLoader] Catálogo com " + tutoriais.size() + " tutoriais povoado com sucesso!");
        }
    }

    private TrickTutorial criarTutorial(String titulo, String descricao, String videoUrl, List<String> passos) {
        TrickTutorial tutorial = new TrickTutorial();
        tutorial.setTitulo(titulo);
        tutorial.setDescricao(descricao);
        tutorial.setVideoUrl(videoUrl);
        tutorial.setPassos(passos);
        return tutorial;
    }
}