package student.management.backend.application.dto;
import student.management.backend.domain.model.Aluno;
import java.util.List;

public class PaginaResultado {

    private final List<Aluno> conteudo;
    private final int paginaAtual;
    private final int totalPaginas;
    private final long totalElementos;
    private final boolean ultima;

    public PaginaResultado(
            List<Aluno> conteudo,
            int paginaAtual,
            int totalPaginas,
            long totalElementos,
            boolean ultima
    ) {
        this.conteudo = conteudo;
        this.paginaAtual = paginaAtual;
        this.totalPaginas = totalPaginas;
        this.totalElementos = totalElementos;
        this.ultima = ultima;
    }

    public List<Aluno> getConteudo() {
        return conteudo;
    }

    public int getPaginaAtual() {
        return paginaAtual;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public long getTotalElementos() {
        return totalElementos;
    }

    public boolean isUltima() {
        return ultima;
    }
}