package student.management.backend.domain.port;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {
    String salvar(MultipartFile foto);
}