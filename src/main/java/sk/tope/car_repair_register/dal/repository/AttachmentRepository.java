package sk.tope.car_repair_register.dal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sk.tope.car_repair_register.dal.domain.Attachment;

import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    @Query("select a from Attachment a left join fetch a.file where a.id = :id")
    Optional<Attachment> findByIdWithFile(@Param("id") Long id);
}
