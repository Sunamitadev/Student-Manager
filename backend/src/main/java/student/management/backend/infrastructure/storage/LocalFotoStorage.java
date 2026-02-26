package student.management.backend.infrastructure.storage;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import student.management.backend.domain.port.FotoStorage;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Component
public class LocalFotoStorage implements FotoStorage {

    private static final String UPLOAD_DIR = "uploads";

    @PostConstruct
    public void init() {
        try {
            Path pasta = Paths.get(UPLOAD_DIR);
            if (!Files.exists(pasta)) {
                Files.createDirectories(pasta);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar diretório de uploads");
        }
    }

    @Override
    public String salvar(MultipartFile foto) {

        try {
            String extensao = foto.getOriginalFilename()
                    .substring(foto.getOriginalFilename().lastIndexOf("."));

            String nomeArquivo = UUID.randomUUID() + extensao;

            Path pasta = Paths.get("uploads");
            if (!Files.exists(pasta)) {
                Files.createDirectories(pasta);
            }

            Path caminho = pasta.resolve(nomeArquivo);
            Files.copy(foto.getInputStream(), caminho);

            return nomeArquivo;

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar foto");
        }
    }
}