package student.management.backend.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import student.management.backend.domain.model.Aluno;
import student.management.backend.domain.model.Status;
import student.management.backend.domain.repository.AlunoRepository;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

class DeletarAlunoUseCaseTest {

    @Mock
    private AlunoRepository repository;

    private DeletarAlunoUseCase useCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        useCase = new DeletarAlunoUseCase(repository);
    }

    @Test
    void deveInativarAlunoSeEstiverAtivo() {

        UUID id = UUID.randomUUID();

        Aluno aluno = Aluno.novo(
                "Carlos",
                "carlos@email.com",
                "81999999999",
                "12345678901",
                "foto.png"
        );

        when(repository.buscarPorId(id)).thenReturn(Optional.of(aluno));

        useCase.execute(id);

        // Deve salvar (porque estava ativo e virou inativo)
        verify(repository, times(1)).salvar(aluno);

        // NÃO deve deletar direto
        verify(repository, never()).deletarPorId(id);
    }

    @Test
    void deveDeletarAlunoSeJaEstiverInativo() {

        UUID id = UUID.randomUUID();

        Aluno aluno = Aluno.novo(
                "Pedro",
                "pedro@email.com",
                "81999999999",
                "12345678901",
                "foto.png"
        );

        // Deixamos o aluno inativo antes de executar
        aluno.inativar();

        when(repository.buscarPorId(id)).thenReturn(Optional.of(aluno));

        useCase.execute(id);

        // Agora deve deletar direto
        verify(repository, times(1)).deletarPorId(id);

        // NÃO deve salvar
        verify(repository, never()).salvar(aluno);
    }
}