package student.management.backend.domain.usecase;

import student.management.backend.application.dto.AlunoRequestDTO;

import java.util.UUID;

public interface DeletarAluno {
    void execute( UUID id);

}
