package student.management.backend.application.usecase;

import student.management.backend.domain.model.Aluno;
import student.management.backend.domain.model.Status;
import student.management.backend.domain.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeletarAlunoUseCase {

    private final AlunoRepository repository;

    public DeletarAlunoUseCase(AlunoRepository repository) {
        this.repository = repository;
    }

    public void execute(UUID id) {
        Aluno aluno = repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        if (aluno.getStatus() == Status.ATIVO) {
            aluno.inativar();
            repository.salvar(aluno);
        } else {
            repository.deletarPorId(id);
        }
    }
}