package br.edu.ifpe.CRMHealthLink.domain.repository;

import br.edu.ifpe.CRMHealthLink.domain.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRespository extends JpaRepository<Exam, Long> {
}
