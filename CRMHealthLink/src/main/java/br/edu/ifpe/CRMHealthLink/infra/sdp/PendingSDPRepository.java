package br.edu.ifpe.CRMHealthLink.infra.sdp;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PendingSDPRepository extends JpaRepository<PendingSDP,Long> {



}
