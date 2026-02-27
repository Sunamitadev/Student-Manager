package student.management.backend.application.usecase;

import org.springframework.stereotype.Service;
import student.management.backend.application.dto.AlunoRequestDTO;
import student.management.backend.domain.model.Aluno;
import student.management.backend.domain.repository.AlunoRepository;
import student.management.backend.domain.usecase.CriarAluno;

@Service
public class CriarAlunoUseCase implements CriarAluno {

    private final AlunoRepository repository;

    public CriarAlunoUseCase(AlunoRepository repository) {
        this.repository = repository;
    }

    @Override
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