package student.management.backend.application.usecase;

import org.springframework.stereotype.Service;
import student.management.backend.domain.model.Aluno;
import student.management.backend.domain.repository.AlunoRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class BuscarAlunoPorIdUseCase {

    private final AlunoRepository repository;

    public BuscarAlunoPorIdUseCase(AlunoRepository repository) {
        this.repository = repository;
    }

    public Optional<Aluno> execute(UUID id) {
        return repository.buscarPorId(id);
    }
}