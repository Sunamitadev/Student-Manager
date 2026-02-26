package student.management.backend.application.dto;

import java.util.List;

public record PaginaResultadoDTO(
        List<AlunoResponseDTO> conteudo,
        int paginaAtual,
        int totalPaginas,
        long totalElementos,
        boolean ultima
) {}