package student.management.backend.application.usecase;

import org.springframework.stereotype.Service;
import student.management.backend.domain.model.Usuario;
import student.management.backend.domain.usecase.Login;

import java.util.regex.Pattern;

@Service
public class LoginUseCase implements Login {

    private static final Pattern REGRA_CREDENCIAL =
            Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d).{8,}$");

    private static final Usuario USUARIO_MOCK = Usuario.mock();

    public String execute(String usuario, String senha) {

        validar(usuario, "Usuário");
        validar(senha, "Senha");

        if (!USUARIO_MOCK.getUsuario().equals(usuario)
                || !USUARIO_MOCK.senhaValida(senha)) {
            throw new RuntimeException("Usuário ou senha inválidos");
        }

        return "token-fake-123";
    }

    private void validar(String valor, String campo) {
        if (valor == null || !REGRA_CREDENCIAL.matcher(valor).matches()) {
            throw new RuntimeException(
                    campo + " deve conter no mínimo 8 caracteres, letras e números"
            );
        }
    }
}