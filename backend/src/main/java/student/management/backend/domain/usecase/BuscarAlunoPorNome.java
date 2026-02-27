package student.management.backend.domain.usecase;

import student.management.backend.domain.model.Aluno;
import java.util.List;

public interface BuscarAlunoPorNome {
   List<Aluno> execute(String nome);
}
