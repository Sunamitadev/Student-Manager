package student.management.backend.application.dto;

// DTO para receber os dados do aluno na criação ou atualização evitar expor a entidade diretamente
public record AlunoRequestDTO(
        String nome,
        String email,
        String telefone,
        String cpf,
        String foto

) {

}


