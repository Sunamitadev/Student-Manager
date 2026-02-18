package student.management.backend.application.usecase;

import org.springframework.stereotype.Service;
import student.management.backend.application.dto.PaginaResultado;
import student.management.backend.domain.repository.AlunoRepository;
import org.springframework.data.domain.Sort ;
import org.springframework.data.domain.PageRequest;
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