package student.management.backend.infrastructure.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import student.management.backend.application.dto.PaginaResultado;
import student.management.backend.domain.model.Aluno;
import student.management.backend.domain.repository.AlunoRepository;
import student.management.backend.infrastructure.persistence.entity.AlunoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.StreamSupport.stream;

@Repository
public class AlunoRepositoryImpl implements AlunoRepository {

    private final AlunoJpaRepository jpaRepository;

    public AlunoRepositoryImpl(AlunoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void salvar(Aluno aluno) {
        jpaRepository.save(AlunoEntity.fromDomain(aluno));
    }
    @Override
    public Optional<Aluno> buscarPorId(UUID id) {
        return jpaRepository.findById(id)
                .map(AlunoEntity::toDomain);
    }

    @Override
    public List<Aluno> listarTodos() {
        return jpaRepository.findAll()
                .stream()
                .map(AlunoEntity::toDomain)
                .toList();
    }

    @Override
    public void deletarPorId(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public PaginaResultado listarPaginado(int pagina, int tamanho) {

        Page<AlunoEntity> page = jpaRepository.findAll(
                PageRequest.of(pagina, tamanho, Sort.by("matricula").descending())
        );

        List<Aluno> conteudo = page.getContent()
                .stream()
                .map(AlunoEntity::toDomain)
                .toList();

        return new PaginaResultado(
                conteudo,
                pagina,
                page.getTotalPages(),
                page.getTotalElements(),
                page.isLast()
        );
    }




}