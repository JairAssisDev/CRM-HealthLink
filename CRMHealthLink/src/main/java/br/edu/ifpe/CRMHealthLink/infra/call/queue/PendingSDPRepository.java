package br.edu.ifpe.CRMHealthLink.infra.call.queue;

import br.edu.ifpe.CRMHealthLink.infra.call.queue.PendingSDP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PendingSDPRepository extends JpaRepository<PendingSDP,Long> {

    void deleteBySourceSessionId(String sessionId);

}
