package br.edu.ifpe.CRMHealthLink.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;


@Entity
@Table(name="doctor_availability")
public record DoctorAvailability(@NotNull LocalDateTime beginTime,@NotNull LocalDateTime endTime,@ManyToOne @Id Doctor doctor) {

}
