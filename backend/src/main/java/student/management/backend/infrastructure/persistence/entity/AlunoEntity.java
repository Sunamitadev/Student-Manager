package student.management.backend.infrastructure.persistence.entity;

import jakarta.persistence.*;
import student.management.backend.domain.model.Aluno;
import student.management.backend.domain.model.Status;

import java.util.UUID;


@Entity
@Table(name = "alunos")
public class AlunoEntity {


    @Id
    private UUID id;

    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private  String matricula;
    private String foto;

    @Enumerated(EnumType.STRING)
    private Status status;

    protected AlunoEntity() {}

    public AlunoEntity(UUID id, String nome, String email, String telefone,String matricula, String cpf, Status status, String foto) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cpf = cpf;
        this.matricula = matricula;
        this.status = status;
        this.foto = foto;
    }

    public static AlunoEntity fromDomain(Aluno aluno) {
        return new AlunoEntity(
                aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getTelefone(),
                aluno.getMatricula(),
                aluno.getCpf(),
                aluno.getStatus(),
                aluno.getFoto()
        );
    }

    public Aluno toDomain() {

        return Aluno.reconstruir (id, nome, email, telefone,matricula, cpf, status, foto);
    }
}