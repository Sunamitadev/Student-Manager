package student.management.backend.domain.model;

import java.util.UUID;

public class Usuario {

    private final UUID id;
    private final String usuario;
    private final String senha;

    private Usuario(UUID id, String usuario, String senha) {
        this.id = id;
        this.usuario = usuario;
        this.senha = senha;
    }

    public static Usuario mock() {
        return new Usuario(
                UUID.fromString("11111111-1111-1111-1111-111111111111"),
                "gestao2026",
                "gestao1234"
        );
    }

    public boolean senhaValida(String senhaInformada) {
        return this.senha.equals(senhaInformada);
    }

    public String getUsuario() {
        return usuario;
    }
}