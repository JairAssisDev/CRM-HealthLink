package br.edu.ifpe.CRMHealthLink.repository;

import br.edu.ifpe.CRMHealthLink.domain.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IExamRespository extends JpaRepository<Exam, Long> {
}
