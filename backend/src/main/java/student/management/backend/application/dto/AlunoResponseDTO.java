package student.management.backend.application.dto;

import java.util.UUID;

public record AlunoResponseDTO(
        UUID id,
        String nome,
        String email,
        String telefone,
        String status
) {}