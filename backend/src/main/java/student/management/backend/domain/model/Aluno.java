package student.management.backend.domain.model;

import java.util.Random;
import java.util.UUID;

/**
 * Entidade de DOMÍNIO.
 * Não conhece banco, JPA, controller ou Spring.
 * Contém apenas regras de negócio.
 */
public class Aluno {

    private UUID id;
    private String nome;
    private String email;
    private String telefone;
    private String matricula;
    private Status status;
    private  String cpf;
    private  String foto;

    //  Construtor privado
    // Força criação controlada via factory method
    private Aluno(UUID id,
                  String nome,
                  String email,
                  String telefone,
                  String matricula,
                  String cpf,
                  Status status,
                  String foto) {

        if (cpf == null || cpf.isBlank()){
            throw new IllegalStateException("CPF é obrigatório");
        }

        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.matricula = matricula;
        this.cpf = cpf;
        this.status = status;
        this.foto = foto;
    }

    public static Aluno reconstruir(
            UUID id,
            String nome,
            String email,
            String telefone,
            String matricula,
            String cpf,
            Status status,
            String foto) {

        return new Aluno(id, nome, email, telefone, matricula, cpf,status, foto);
    }

    // =========================
    // FACTORY METHOD
    // =========================

    /**
     * Cria um novo aluno ATIVO.
     */
    public static Aluno novo(String nome,
                             String email,
                             String telefone,
                             String cpf,
                             String foto) {

        return new Aluno(
                UUID.randomUUID(),
                nome,
                email,
                telefone,
                gerarMatricula(),
                cpf,
                Status.ATIVO,
                foto
        );
    }



    // =========================
    // REGRAS DE NEGÓCIO
    // =========================

    /**
     * Atualiza dados básicos do aluno.
     */

    private  static String gerarMatricula(){
        int numero = new Random().nextInt(900000) + 100000; // Gera um número aleatório de 6 dígitos
        return "MAT-"+ numero;
    }
    public void atualizarDados( String email, String telefone, String foto) {

        this.email = email;
        this.telefone = telefone;
        this.foto = foto;
    }
    public void alterarStatus(Status status){
        this.status =status;
    }

    /**
     * Inativa o aluno.
     */
    public void inativar() {
        if (this.status == Status.INATIVO) {
            throw new IllegalStateException("Aluno já está inativo");
        }
        this.status = Status.INATIVO;
    }

    /**
     * Reativa o aluno.
     */
    public void ativar() {
        if (this.status == Status.ATIVO) {
            throw new IllegalStateException("Aluno já está ativo");
        }
        this.status = Status.ATIVO;
    }

    // =========================
    // GETTERS
    // =========================

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public Status getStatus() {
        return status;
    }

    public String getMatricula() {
        return matricula;
    }

    public  String getFoto() {
        return foto;
    }

}