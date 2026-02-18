package student.management.backend.presentation.dto;

/**
 * DTO de saída para login
 * Retorna o token de autenticação
 */
public record TokenResponse(
        String token
) {
}