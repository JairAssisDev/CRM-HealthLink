package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.dto.examDto.ExamCreateDto;
import br.edu.ifpe.CRMHealthLink.dto.mapper.ExamMapper;
import br.edu.ifpe.CRMHealthLink.entity.Exam;
import br.edu.ifpe.CRMHealthLink.repository.ExamRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExamService {
    private final ExamRespository examRespository;
    private final ExamMapper examMapper;

    @Transactional
    public Exam save(Exam exam) {return examRespository.save(exam);}

    @Transactional
    public List<Exam> getAllExams() {return examRespository.findAll();}

    @Transactional
    public Exam findById(Long id) {
        return examRespository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Exam não encontrado"));
    }

    @Transactional
    public void delete(Long id) {examRespository.deleteById(id);}

    @Transactional
    public void update(Long id , ExamCreateDto examCreateDto) {
        Exam examToUpdate = findById(id);

        Exam updateExam = examMapper.toExam(examCreateDto);

        examToUpdate.setAppointment(updateExam.getAppointment());
        examToUpdate.setDate(updateExam.getDate());
        examToUpdate.setDescription(updateExam.getDescription());

        examRespository.save(examToUpdate);
    }
}
