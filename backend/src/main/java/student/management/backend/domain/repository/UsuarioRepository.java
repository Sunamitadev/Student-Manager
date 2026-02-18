package student.management.backend.domain.repository;

import student.management.backend.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository {

    Optional<Usuario> buscarPorEmail(String email);
}