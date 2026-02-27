package student.management.backend.domain.usecase;

import student.management.backend.application.dto.AlunoRequestDTO;
import student.management.backend.application.dto.AtualizarAlunoDTO;

import java.util.UUID;

public interface AtualizarAluno {
    void execute(UUID id, AtualizarAlunoDTO dto);
}
