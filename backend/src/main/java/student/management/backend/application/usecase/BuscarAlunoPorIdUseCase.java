package student.management.backend.application.usecase;

import org.springframework.stereotype.Service;
import student.management.backend.domain.model.Aluno;
import student.management.backend.domain.repository.AlunoRepository;
import student.management.backend.domain.usecase.BuscarAlunoPorId;

import java.util.Optional;
import java.util.UUID;

@Service
public class BuscarAlunoPorIdUseCase implements BuscarAlunoPorId {

    private final AlunoRepository repository;

    public BuscarAlunoPorIdUseCase(AlunoRepository repository) {
        this.repository = repository;
    }
    @Override
    public Optional<Aluno> execute(UUID id) {
        return repository.buscarPorId(id);
    }


}