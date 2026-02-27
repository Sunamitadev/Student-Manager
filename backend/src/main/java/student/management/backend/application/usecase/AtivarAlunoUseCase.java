package student.management.backend.application.usecase;

import org.springframework.stereotype.Service;
import student.management.backend.domain.model.Aluno;
import student.management.backend.domain.repository.AlunoRepository;
import student.management.backend.domain.usecase.AtivarAluno;

import java.util.UUID;

@Service
public class AtivarAlunoUseCase implements AtivarAluno {

    private final AlunoRepository repository;

    public AtivarAlunoUseCase(AlunoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(UUID id) {

        Aluno aluno = repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        aluno.ativar();
        repository.salvar(aluno);
    }
}