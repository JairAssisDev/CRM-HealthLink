package br.edu.ifpe.CRMHealthLink.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;


@Entity
@Table(name="doctor_availability")
@NoArgsConstructor
@Getter
@Setter
public class DoctorAvailability {
    private static long AVG_APPOINTMENT_TIME = 15;

    public DoctorAvailability(LocalDateTime beginTime,LocalDateTime endTime,Doctor doctor){
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.doctor = doctor;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime beginTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    private Long tickets;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Doctor doctor;


    @PrePersist
    private void setAmountOfTickets(){
        this.tickets = Math.round((double)Duration.between(beginTime,endTime).toMinutes() / AVG_APPOINTMENT_TIME);
    }
}
