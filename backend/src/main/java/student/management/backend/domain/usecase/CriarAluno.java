package student.management.backend.domain.usecase;

import student.management.backend.application.dto.AlunoRequestDTO;

public interface CriarAluno {
    void execute(AlunoRequestDTO dto);
}
