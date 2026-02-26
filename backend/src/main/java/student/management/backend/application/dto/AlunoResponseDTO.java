package student.management.backend.application.dto;

import student.management.backend.domain.model.Aluno;

import java.util.UUID;

public record AlunoResponseDTO(
        UUID id,
        String nome,
        String email,
        String telefone,
        String status,
        String cpf,
        String matricula,
        String foto
) {
    public static AlunoResponseDTO fromDomain(Aluno aluno){
        return new AlunoResponseDTO(
                aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getTelefone(),
                aluno.getStatus().name(),
                aluno.getCpf(),
                aluno.getMatricula(),
                aluno.getFoto()

        );
    }
}