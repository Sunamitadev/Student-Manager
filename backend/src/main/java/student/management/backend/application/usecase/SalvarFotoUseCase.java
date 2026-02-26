package student.management.backend.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import student.management.backend.domain.port.FotoStorage;

@Service
public class SalvarFotoUseCase {

    private final FotoStorage storage;

    public SalvarFotoUseCase(FotoStorage storage) {
        this.storage = storage;
    }


    public String execute(MultipartFile foto) {

        if (foto == null || foto.isEmpty()) {
            throw new RuntimeException("Foto é obrigatória");
        }

        if (!foto.getContentType().startsWith("image/")) {
                throw new RuntimeException("Arquivo inválido");
        }

            return storage.salvar(foto);
        }


}