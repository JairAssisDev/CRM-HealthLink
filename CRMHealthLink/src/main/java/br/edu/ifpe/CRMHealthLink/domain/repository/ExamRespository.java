package br.edu.ifpe.CRMHealthLink.repository;

import br.edu.ifpe.CRMHealthLink.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRespository extends JpaRepository<Exam, Long> {
}
