package student.management.backend.presentation.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import student.management.backend.application.dto.AlunoRequestDTO;
import student.management.backend.application.dto.PaginaResultado;
import student.management.backend.application.usecase.*;
import student.management.backend.domain.model.Aluno;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.management.backend.domain.model.Status;
import student.management.backend.presentation.dto.AtualizarAlunoRequest;
import org.springframework.web.multipart.MultipartFile ;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final CriarAlunoUseCase criarAlunoUseCase;
    private final ListarAlunoUseCase listar;
    private final DeletarAlunoUseCase deletar;
    private final BuscarAlunoPorNomeUseCase buscarPorNome;
    private final AtualizarAlunoUseCase atualizarAluno;
    private final AtivarAlunoUseCase ativarAluno;
    private final BuscarAlunoPorIdUseCase buscarPorIdUseCase;


    public AlunoController(
            CriarAlunoUseCase criar,
            ListarAlunoUseCase listar,
            DeletarAlunoUseCase deletar,
            BuscarAlunoPorNomeUseCase buscarPorNome,
            AtualizarAlunoUseCase atualizarAluno,
            AtivarAlunoUseCase ativarAluno,
            BuscarAlunoPorIdUseCase buscarPorIdUseCase
    ) {
        this.criarAlunoUseCase = criar;
        this.listar = listar;
        this.deletar = deletar;
        this.buscarPorNome = buscarPorNome;
        this.atualizarAluno = atualizarAluno;
        this.ativarAluno = ativarAluno;
        this.buscarPorIdUseCase = buscarPorIdUseCase;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> criarAluno(
            @RequestHeader("Authorization") String token,
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam String telefone,
            @RequestParam String cpf,
            @RequestParam MultipartFile foto
    ) throws Exception {

        if (!"Bearer token-fake-123".equals(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String nomeArquivo = criarFoto(foto);

        AlunoRequestDTO dto = new AlunoRequestDTO(
                nome,
                email,
                telefone,
                cpf,
                nomeArquivo
        );

        criarAlunoUseCase.execute(dto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private static String criarFoto(MultipartFile foto) throws IOException {
        String extensao = foto.getOriginalFilename()
                .substring(foto.getOriginalFilename().lastIndexOf("."));

        String nomeArquivo = UUID.randomUUID() + extensao;

        Path pasta = Paths.get("uploads");
        if (!Files.exists(pasta)) {
            Files.createDirectories(pasta);
        }

        Path caminho = pasta.resolve(nomeArquivo);

        Files.copy(foto.getInputStream(), caminho);
        return nomeArquivo;
    }

    // LISTAR COM PAGINAÇÃO
    @GetMapping
    public ResponseEntity<PaginaResultado> listar(
            @RequestParam(defaultValue = "0") int page
    ) {
        return ResponseEntity.ok(listar.listar(page));
    }

    private List<Aluno> listarSemPaginacao() {
        return buscarPorNome.execute(""); // retorna todos
    }

    // 🔍 Buscar por nome
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorNome(
            @RequestParam String nome

    ) {
        return ResponseEntity.ok(buscarPorNome.execute(nome));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable UUID id,
            @RequestParam String email,
            @RequestParam String telefone,
            @RequestParam MultipartFile foto,
            @RequestParam Status status
    ) throws IOException {
        String nomeArquivo = criarFoto(foto);

        AtualizarAlunoRequest dto = new AtualizarAlunoRequest();
        dto.setEmail(email);
        dto.setTelefone(telefone);
        dto.setStatus(status);
        dto.setFoto(nomeArquivo);

        atualizarAluno.execute(
                id,
               dto
        );

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable UUID id) {
        ativarAluno.execute(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        deletar.execute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable UUID id) {

        return buscarPorIdUseCase.execute(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}