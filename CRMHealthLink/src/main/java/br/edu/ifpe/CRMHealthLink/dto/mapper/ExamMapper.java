package br.edu.ifpe.CRMHealthLink.dto.mapper;

import br.edu.ifpe.CRMHealthLink.dto.examDto.ExamCreateDto;
import br.edu.ifpe.CRMHealthLink.dto.examDto.ExamResponseDto;
import br.edu.ifpe.CRMHealthLink.entity.Appointment;
import br.edu.ifpe.CRMHealthLink.entity.Exam;
import br.edu.ifpe.CRMHealthLink.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class ExamMapper {
    private final AppointmentService appointmentService;

    public Exam toExam(ExamCreateDto examDto){
        Appointment appointment = appointmentService.findById(examDto.getFk_appointment());

        Exam exam = new Exam();
        exam.setDate(examDto.getDate());
        exam.setDescription(examDto.getDescricao());
        exam.setAppointment(appointment);
        return exam;
    }
    public ExamResponseDto toDtoExam(Exam exam){

        ExamResponseDto examResponseDto = new ExamResponseDto();
        examResponseDto.setDate(exam.getDate());
        examResponseDto.setDescription(exam.getDescription());
        examResponseDto.setNameDoctor(exam.getAppointment().getDoctor().getName());
        examResponseDto.setNamePatient(exam.getAppointment().getPatient().getName());
        examResponseDto.setDescriptionAppointment(examResponseDto.getDescriptionAppointment());

        return examResponseDto;
    }
    public List<ExamResponseDto> toDtoExams(List<Exam> exams){
        return exams.stream()
                .map(this::toDtoExam)
                .collect(Collectors.toList());
    }
}
