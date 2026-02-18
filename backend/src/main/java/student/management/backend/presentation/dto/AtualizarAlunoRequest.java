package student.management.backend.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import student.management.backend.domain.model.Status;

public class AtualizarAlunoRequest {

    @NotBlank
    private String nome;

    @Email
    private String email;

    @NotBlank
    private String telefone;

    private  String cpf;
    private Status status;
    private String foto;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    // ===== GETTERS =====
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

    public Status  getStatus() {
        return status;
    }

    public String getFoto() {
        return foto;
    }
}