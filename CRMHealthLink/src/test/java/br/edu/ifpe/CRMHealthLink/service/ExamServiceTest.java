package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.controller.dto.examDto.ExamCreateDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.mapper.ExamMapper;
import br.edu.ifpe.CRMHealthLink.domain.entity.Exam;
import br.edu.ifpe.CRMHealthLink.repository.IExamRespository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExamServiceTest {

    @Mock
    private IExamRespository examRepository;

    @Mock
    private ExamMapper examMapper;

    @InjectMocks
    private ExamService examService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveExam() {
        // Arrange
        Exam exam = new Exam();
        when(examRepository.save(exam)).thenReturn(exam);

        // Act
        Exam result = examService.save(exam);

        // Assert
        assertNotNull(result);
        verify(examRepository, times(1)).save(exam);
    }

    @Test
    void testGetAllExams() {
        // Arrange
        List<Exam> exams = List.of(new Exam(), new Exam());
        when(examRepository.findAll()).thenReturn(exams);

        // Act
        List<Exam> result = examService.getAllExams();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(examRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        // Arrange
        Long id = 1L;
        Exam exam = new Exam();
        exam.setId(id);
        when(examRepository.findById(id)).thenReturn(Optional.of(exam));

        // Act
        Exam result = examService.findById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(examRepository, times(1)).findById(id);
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        Long id = 999L;
        when(examRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> examService.findById(id));
        assertEquals("Exam não encontrado", exception.getMessage());
        verify(examRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteExam() {
        // Arrange
        Long id = 1L;

        // Act
        examService.delete(id);

        // Assert
        verify(examRepository, times(1)).deleteById(id);
    }

    @Test
    void testUpdateExam() {
        // Arrange
        Long id = 1L;
        ExamCreateDto examDto = new ExamCreateDto();
        examDto.setDescricao("Updated Description");

        Exam existingExam = new Exam();
        existingExam.setId(id);

        Exam updatedExam = new Exam();
        updatedExam.setDescription("Updated Description");

        when(examRepository.findById(id)).thenReturn(Optional.of(existingExam));
        when(examMapper.toExam(examDto)).thenReturn(updatedExam);
        when(examRepository.save(existingExam)).thenReturn(existingExam);

        // Act
        examService.update(id, examDto);

        // Assert
        assertEquals("Updated Description", existingExam.getDescription());
        verify(examRepository, times(1)).findById(id);
        verify(examRepository, times(1)).save(existingExam);
    }

    @Test
    void testUpdateExam_NotFound() {
        // Arrange
        Long id = 999L;
        ExamCreateDto examDto = new ExamCreateDto();
        when(examRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> examService.update(id, examDto));
        assertEquals("Exam não encontrado", exception.getMessage());
        verify(examRepository, times(1)).findById(id);
    }
}
