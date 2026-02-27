package student.management.backend.application.usecase;

import org.springframework.stereotype.Service;
import student.management.backend.domain.model.PaginaResultado;
import student.management.backend.domain.repository.AlunoRepository;

@Service
public class ListarAlunoUseCase {

    private static final int TAMANHO_PAGINA = 10;

    private final AlunoRepository repository;

    public ListarAlunoUseCase(AlunoRepository repository) {
        this.repository = repository;
    }

    public PaginaResultado listar(int pagina) {
        return repository.listarPaginado(pagina, TAMANHO_PAGINA);
    }




}