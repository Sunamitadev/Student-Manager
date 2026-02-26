package student.management.backend.application.usecase;

import org.springframework.web.multipart.MultipartFile;

public interface SalvarFoto {
    String salvar(MultipartFile foto);
}
