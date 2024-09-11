package br.edu.ifpe.CRMHealthLink.domain.useCase;

import java.util.List;

public interface ICrudService<T>{
    public T save(T entity);
    public List<T> getAllAppointment();

    public T findById(Long id);

    public void delete(Long id);
    public void update(Long id, T entity);
}
