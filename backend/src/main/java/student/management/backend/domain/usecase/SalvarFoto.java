package student.management.backend.domain.usecase;

import org.springframework.web.multipart.MultipartFile;

public interface SalvarFoto {
    String salvar(MultipartFile foto);
}
