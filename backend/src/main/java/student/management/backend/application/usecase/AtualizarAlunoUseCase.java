package student.management.backend.application.usecase;

import student.management.backend.application.dto.AtualizarAlunoDTO;
import student.management.backend.domain.model.Aluno;
import student.management.backend.domain.repository.AlunoRepository;
import org.springframework.stereotype.Service;
import student.management.backend.domain.usecase.AtualizarAluno;

import java.util.UUID;
@Service
public class AtualizarAlunoUseCase implements AtualizarAluno {

    private final AlunoRepository repository;

    public AtualizarAlunoUseCase(AlunoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(UUID id, AtualizarAlunoDTO dto) {

        var aluno = repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        aluno.atualizarDados(
                dto.email(),
                dto.telefone(),
                dto.foto()
        );

        aluno.alterarStatus(dto.status());

        repository.salvar(aluno);
    }
}