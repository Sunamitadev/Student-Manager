package student.management.backend.presentation.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import student.management.backend.application.dto.*;
import student.management.backend.application.usecase.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.management.backend.domain.model.PaginaResultado;
import student.management.backend.domain.model.Status;
import org.springframework.web.bind.annotation.RequestParam;

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
    private  final SalvarFotoUseCase salvarFotoUseCase;

    public AlunoController(
            CriarAlunoUseCase criar,
            ListarAlunoUseCase listar,
            DeletarAlunoUseCase deletar,
            BuscarAlunoPorNomeUseCase buscarPorNome,
            AtualizarAlunoUseCase atualizarAluno,
            AtivarAlunoUseCase ativarAluno,
            BuscarAlunoPorIdUseCase buscarPorIdUseCase, SalvarFotoUseCase salvarFotoUseCase
    ) {
        this.criarAlunoUseCase = criar;
        this.listar = listar;
        this.deletar = deletar;
        this.buscarPorNome = buscarPorNome;
        this.atualizarAluno = atualizarAluno;
        this.ativarAluno = ativarAluno;
        this.buscarPorIdUseCase = buscarPorIdUseCase;
        this.salvarFotoUseCase = salvarFotoUseCase;
    }
//aceita o envio da foto
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
        //atualizei aqui para usar o use case de salvar foto, que pode ter regras de negócio específicas
       String nomeArquivo = salvarFotoUseCase.execute(foto);

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

  
    // LISTAR COM PAGINAÇÃO
    @GetMapping
    public ResponseEntity<PaginaResultadoDTO> listar(
            @RequestParam(defaultValue = "0") int page
    ) {

        PaginaResultado resultado = listar.listar(page);

        List<AlunoResponseDTO> conteudoDTO = resultado.getConteudo()
                .stream()
                .map(AlunoResponseDTO::fromDomain)
                .toList();

        PaginaResultadoDTO dto = new PaginaResultadoDTO(
                conteudoDTO,
                resultado.getPaginaAtual(),
                resultado.getTotalPaginas(),
                resultado.getTotalElementos(),
                resultado.isUltima()
        );

        return ResponseEntity.ok(dto);
    }

    // 🔍 Buscar por nome
    @GetMapping("/buscar")
    public ResponseEntity<List<AlunoResponseDTO>> buscarPorNome(
            @RequestParam String nome
    ) {

        List<AlunoResponseDTO> alunos = buscarPorNome.execute(nome)
                .stream()
                .map(AlunoResponseDTO::fromDomain)
                .toList();

        return ResponseEntity.ok(alunos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable UUID id,
            @RequestParam String email,
            @RequestParam String telefone,
           @RequestParam(required = false) MultipartFile foto,
           // @RequestParam MultipartFile foto,
            @RequestParam Status status
    ) {

        String nomeArquivo = null;

        if (foto != null && !foto.isEmpty()) {
            nomeArquivo = salvarFotoUseCase.execute(foto);
        }

        AtualizarAlunoDTO dto = new AtualizarAlunoDTO(
                email,
                telefone,
                status,
                nomeArquivo
        );

        atualizarAluno.execute(id, dto);

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
    public ResponseEntity<AlunoResponseDTO> buscarPorId(@PathVariable UUID id) {

        return buscarPorIdUseCase.execute(id)
                .map(AlunoResponseDTO::fromDomain)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}