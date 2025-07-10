// ConquistaService.java (COMPLETO E ATUALIZADO)

package com.skateholders.skateholders.services; // Ou o pacote mongoServs se preferir

import com.skateholders.skateholders.DTOs.ConquistaStatusDTO; // <-- Um DTO novo que vamos criar
import com.skateholders.skateholders.models.TrickUsuario;
import com.skateholders.skateholders.models.Usuario;
import com.skateholders.skateholders.mongoDocs.ConquistaDoc;
import com.skateholders.skateholders.mongoDocs.UsuarioConquistaDoc;
import com.skateholders.skateholders.mongoReps.ConquistaDocRepository;
import com.skateholders.skateholders.mongoReps.UsuarioConquistaDocRepository;
import com.skateholders.skateholders.repositories.AtividadeRepository; // <-- Import do SQL Repo
import com.skateholders.skateholders.repositories.TrickUsuarioRepository; // <-- Import do SQL Repo
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ConquistaService {

    // --- Repositórios MongoDB ---
    @Autowired
    private ConquistaDocRepository conquistaRepository;

    @Autowired
    private UsuarioConquistaDocRepository usuarioConquistaRepository;

    // --- Repositórios SQL (para verificação de requisitos) ---
    @Autowired
    private TrickUsuarioRepository trickUsuarioRepository; // SQL

    @Autowired
    private AtividadeRepository atividadeRepository; // SQL

    /**
     * Lista todas as conquistas existentes e o status delas para o usuário logado.
     * Este método é o que o frontend chamará para renderizar as "cartas".
     */
    public List<ConquistaStatusDTO> getStatusConquistasParaUsuarioLogado() {
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<ConquistaDoc> todasConquistas = conquistaRepository.findAll();
        List<UsuarioConquistaDoc> conquistasDesbloqueadas = usuarioConquistaRepository.findByUsuarioId(usuarioLogado.getId());

        // Cria um mapa para busca rápida das conquistas já desbloqueadas
        Map<String, UsuarioConquistaDoc> mapaDesbloqueadas = conquistasDesbloqueadas.stream()
                .collect(Collectors.toMap(UsuarioConquistaDoc::getConquistaId, c -> c));

        // Transforma a lista de conquistas em DTOs, indicando se já foi desbloqueada ou não
        return todasConquistas.stream()
                .map(conquista -> new ConquistaStatusDTO(conquista, mapaDesbloqueadas.containsKey(conquista.getId())))
                .collect(Collectors.toList());
    }

    /**
     * Tenta desbloquear uma conquista para o usuário logado.
     * Este é o método chamado pelo botão "Reivindicar Conquista".
     */
    @Transactional
    public boolean reivindicarConquista(String conquistaId) {
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 1. Verifica se o usuário já não possui a conquista
        if (usuarioConquistaRepository.findByUsuarioIdAndConquistaId(usuarioLogado.getId(), conquistaId).isPresent()) {
            throw new IllegalStateException("Conquista já desbloqueada.");
        }

        // 2. Busca as regras da conquista no MongoDB
        ConquistaDoc conquista = conquistaRepository.findById(conquistaId)
                .orElseThrow(() -> new RuntimeException("Conquista não encontrada."));

        // 3. Verifica se os requisitos foram atendidos
        boolean requisitosAtendidos = verificarRequisitos(usuarioLogado, conquista);

        if (requisitosAtendidos) {
            // 4. Se foram, salva a conquista como desbloqueada
            UsuarioConquistaDoc novaConquistaDesbloqueada = new UsuarioConquistaDoc(usuarioLogado.getId(), conquistaId);
            usuarioConquistaRepository.save(novaConquistaDesbloqueada);
            return true;
        }

        return false;
    }

    /**
     * Método privado que centraliza a verificação dos diferentes tipos de conquistas.
     * Ele lê os requisitos do documento MongoDB e consulta o banco SQL.
     */
    private boolean verificarRequisitos(Usuario usuario, ConquistaDoc conquista) {
        Map<String, Object> requisitos = conquista.getRequisitos();

        switch (conquista.getTipo()) {
            case "ACERTOS_TRICK":
                // Requisito: ter um certo número de acertos em uma trick específica
                Long trickId = ((Number) requisitos.get("trickId")).longValue();
                int quantidadeAcertos = (Integer) requisitos.get("quantidade");

                return trickUsuarioRepository.findById(new com.skateholders.skateholders.models.TrickUsuarioId(usuario.getId(), trickId))
                        .map(trickUsuario -> trickUsuario.getAcertos() >= quantidadeAcertos)
                        .orElse(false);

            case "TOTAL_ATIVIDADES":
                // Requisito: ter um número total de atividades registradas
                int quantidadeAtividades = (Integer) requisitos.get("quantidade");
                long totalAtividadesDoUsuario = atividadeRepository.countBySesh_Usuario(usuario);
                return totalAtividadesDoUsuario >= quantidadeAtividades;

            // ADICIONE OUTROS 'case' AQUI PARA TIPOS DIFERENTES DE CONQUISTAS
            // Ex: case "NIVEL_TRICK": ...
            // Ex: case "PROFICIENCIA_TRICK": ...

            default:
                // Se o tipo de conquista não for reconhecido, não pode ser desbloqueada
                return false;
        }
    }
}