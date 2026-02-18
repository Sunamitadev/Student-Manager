package student.management.backend.application.usecase;

import student.management.backend.domain.model.Aluno;
import student.management.backend.domain.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AtivarAlunoUseCase {

    private final AlunoRepository repository;

    public AtivarAlunoUseCase(AlunoRepository repository) {
        this.repository = repository;
    }

    public void execute(UUID id) {

        Aluno aluno = repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        aluno.ativar();

        repository.salvar(aluno);
    }
}