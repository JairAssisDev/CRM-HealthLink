package br.edu.ifpe.CRMHealthLink.infra.sdp;

import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class PendingSDP {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String message;
}
