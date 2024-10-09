package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.domain.entity.Exam;
import br.edu.ifpe.CRMHealthLink.repository.ExamRespository;
import br.edu.ifpe.CRMHealthLink.domain.useCase.IExamService;
import br.edu.ifpe.CRMHealthLink.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExamServiceImpl implements IExamService {
    private final ExamRespository examRespository;

    public Exam save(Exam exam) {return examRespository.save(exam);}


    public List<Exam> getAll() {return examRespository.findAll();}


    public Exam findById(Long id) {
        return examRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found"));
    }


    public void delete(Long id) {examRespository.deleteById(id);}

    @Transactional
    public void update(Long id , Exam examDto) {
        Exam exam = findById(id);

        updateFields(examDto,exam);

        examRespository.save(exam);
    }
}
