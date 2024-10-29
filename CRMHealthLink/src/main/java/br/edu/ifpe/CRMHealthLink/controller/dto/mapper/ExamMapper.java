package br.edu.ifpe.CRMHealthLink.controller.dto.mapper;

import br.edu.ifpe.CRMHealthLink.controller.dto.examDto.ExamResponseDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.examDto.ExamCreateDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.Appointment;
import br.edu.ifpe.CRMHealthLink.domain.entity.Exam;
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

        examResponseDto.setDescriptionAppointment(exam.getAppointment().getDescription());

        return examResponseDto;
    }
    public List<ExamResponseDto> toDtoExams(List<Exam> exams){
        return exams.stream()
                .map(this::toDtoExam)
                .collect(Collectors.toList());
    }
    public ExamResponseDto toDtoExamPatient(Exam exam){

        ExamResponseDto examResponseDto = new ExamResponseDto();

        examResponseDto.setDate(exam.getDate());

        examResponseDto.setDescription(exam.getDescription());

        examResponseDto.setNameDoctor(exam.getAppointment().getDoctor().getName());

        examResponseDto.setNamePatient(exam.getAppointment().getPatient().getName());

        return examResponseDto;
    }
    public List<ExamResponseDto> toDtoExamsPatient(List<Exam> exams){
        return exams.stream()
                .map(this::toDtoExamPatient)
                .collect(Collectors.toList());
    }
}
