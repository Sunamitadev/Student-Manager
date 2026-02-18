package student.management.backend.application.usecase;

import org.springframework.stereotype.Service;
import student.management.backend.domain.model.Aluno;
import student.management.backend.domain.repository.AlunoRepository;

import java.util.List;

@Service
public class BuscarAlunoPorNomeUseCase {

    private final AlunoRepository repository;

    public BuscarAlunoPorNomeUseCase(AlunoRepository repository) {
        this.repository = repository;
    }

    public List<Aluno> execute(String nome ) {

        List<Aluno> alunos = repository.listarTodos();

        if (nome != null && !nome.isBlank()) {
            alunos = alunos.stream()
                    .filter(a -> a.getNome().contains(nome))
                    .toList();
        }



        return alunos;
    }
}