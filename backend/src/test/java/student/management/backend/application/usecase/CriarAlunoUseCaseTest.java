package student.management.backend.application.usecase;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import student.management.backend.application.dto.AlunoRequestDTO;
import student.management.backend.domain.model.Aluno;
import student.management.backend.domain.repository.AlunoRepository;




public class CriarAlunoUseCaseTest {

    @Mock
    private AlunoRepository repository;

    private CriarAlunoUseCase useCase;

    @BeforeEach
  void setup() {
        MockitoAnnotations.openMocks(this);
        useCase = new CriarAlunoUseCase(repository);
    }

    @Test
    void deveCriarAlunoComSucesso() {

        AlunoRequestDTO dto = new AlunoRequestDTO(
                "Maria Silva",
                "maria@email.com",
                "81999999999",
                "12345678901",
                "foto.png"
        );

        useCase.execute(dto);

        verify(repository, times(1)).salvar(any(Aluno.class));
    }
}