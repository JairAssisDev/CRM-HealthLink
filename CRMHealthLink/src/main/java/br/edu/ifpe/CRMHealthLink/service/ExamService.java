package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.controller.dto.examDto.ExamCreateDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.mapper.ExamMapper;
import br.edu.ifpe.CRMHealthLink.domain.entity.Exam;
import br.edu.ifpe.CRMHealthLink.repository.IExamRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExamService {
    private final IExamRespository IExamRespository;
    private final ExamMapper examMapper;

    @Transactional
    public Exam save(Exam exam) {return IExamRespository.save(exam);}

    @Transactional
    public List<Exam> getAllExams() {return IExamRespository.findAll();}

    @Transactional
    public Exam findById(Long id) {
        return IExamRespository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Exam não encontrado"));
    }

    @Transactional
    public void delete(Long id) {
        IExamRespository.deleteById(id);}

    @Transactional
    public void update(Long id , ExamCreateDto examCreateDto) {
        Exam examToUpdate = findById(id);

        Exam updateExam = examMapper.toExam(examCreateDto);

        examToUpdate.setAppointment(updateExam.getAppointment());
        examToUpdate.setDate(updateExam.getDate());
        examToUpdate.setDescription(updateExam.getDescription());

        IExamRespository.save(examToUpdate);
    }
}
