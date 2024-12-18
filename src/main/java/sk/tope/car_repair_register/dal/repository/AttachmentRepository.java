package sk.tope.car_repair_register.dal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tope.car_repair_register.dal.domain.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
