package student.management.backend.domain.repository;

import student.management.backend.application.dto.PaginaResultado;
import student.management.backend.domain.model.Aluno;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlunoRepository {

    PaginaResultado listarPaginado(int pagina, int tamanho);

    void salvar(Aluno aluno);

    Optional<Aluno> buscarPorId(UUID id);

    List<Aluno> listarTodos();

    void deletarPorId(UUID id);


}