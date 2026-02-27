package student.management.backend.domain.usecase;

import student.management.backend.domain.model.Aluno;
import java.util.Optional;
import java.util.UUID;

public interface BuscarAlunoPorId {
   Optional<Aluno> execute(UUID id);
}



