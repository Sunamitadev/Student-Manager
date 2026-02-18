package student.management.backend.application.usecase;

import student.management.backend.domain.model.Aluno;
import student.management.backend.domain.model.Status;
import student.management.backend.domain.repository.AlunoRepository;
import org.springframework.stereotype.Service;
import student.management.backend.presentation.dto.AtualizarAlunoRequest;

import java.util.UUID;
@Service
public class AtualizarAlunoUseCase {

    private final AlunoRepository repository;

    public AtualizarAlunoUseCase(AlunoRepository repository) {
        this.repository = repository;
    }

    public void execute(UUID id, AtualizarAlunoRequest request) {

        Aluno aluno = repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        aluno.atualizarDados(request.getEmail(), request.getTelefone(), request.getFoto());
        aluno.alterarStatus(request.getStatus());
        repository.salvar(aluno);
    }
}