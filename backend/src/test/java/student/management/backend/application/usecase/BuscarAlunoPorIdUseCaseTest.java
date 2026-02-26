package student.management.backend.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import student.management.backend.domain.model.Aluno;
import student.management.backend.domain.repository.AlunoRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuscarAlunoPorIdUseCaseTest {

    @Mock
    private AlunoRepository repository;

    private BuscarAlunoPorIdUseCase useCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        useCase = new BuscarAlunoPorIdUseCase(repository);
    }

    @Test
    void deveRetornarAlunoQuandoExistir() {

        // Arrange (preparação)
        UUID id = UUID.randomUUID();

        Aluno aluno = Aluno.novo(
                "Ana",
                "ana@email.com",
                "81999999999",
                "12345678901",
                "foto.png"
        );

        when(repository.buscarPorId(id)).thenReturn(Optional.of(aluno));

        // Act (execução)
        Optional<Aluno> resultado = useCase.execute(id);

        // Assert (verificação)
        assertTrue(resultado.isPresent());
        verify(repository, times(1)).buscarPorId(id);
    }
}