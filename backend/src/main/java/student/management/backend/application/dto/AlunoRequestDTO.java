package student.management.backend.application.dto;

public record AlunoRequestDTO<cpf>(
        String nome,
        String email,
        String telefone,
        String cpf,
        String foto

) {

}


