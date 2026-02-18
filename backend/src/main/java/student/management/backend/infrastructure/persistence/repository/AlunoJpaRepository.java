package student.management.backend.infrastructure.persistence.repository;

import student.management.backend.infrastructure.persistence.entity.AlunoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import  org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;
public interface AlunoJpaRepository extends JpaRepository<AlunoEntity, UUID> {
    Page<AlunoEntity>findAll(Pageable pageable);

}