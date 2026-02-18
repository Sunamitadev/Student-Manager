package student.management.backend.application.usecase;

import student.management.backend.application.dto.AlunoRequestDTO;
import student.management.backend.domain.model.Aluno;
import student.management.backend.domain.repository.AlunoRepository;
import org.springframework.stereotype.Service;

@Service
public class CriarAlunoUseCase {

    private final AlunoRepository repository;

    public CriarAlunoUseCase(AlunoRepository repository) {
        this.repository = repository;
    }

    public void execute(AlunoRequestDTO dto) {
        Aluno aluno = Aluno.novo(
                dto.nome(),
                dto.email(),
                dto.telefone(),
                dto.cpf(),
                dto.foto()
        );
        repository.salvar(aluno);
    }
}