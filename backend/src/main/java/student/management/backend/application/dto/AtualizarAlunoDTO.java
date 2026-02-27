package student.management.backend.application.dto;

import student.management.backend.domain.model.Status;

public record AtualizarAlunoDTO(
        String email,
        String telefone,
        Status status,
        String foto
) {}