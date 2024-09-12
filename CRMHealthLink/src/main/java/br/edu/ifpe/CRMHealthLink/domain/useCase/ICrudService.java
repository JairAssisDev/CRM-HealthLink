package br.edu.ifpe.CRMHealthLink.domain.useCase;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public interface ICrudService<T> {
    public T save(T entity);

    public List<T> getAll();

    public T findById(Long id);

    public void delete(Long id);

    public void update(Long id, T entity);

    default void updateFields(T source, T mod) {

        List<Class> classes = new ArrayList<>();
        Class currentClass = source.getClass();
        while (currentClass != null) {
            if (currentClass != Object.class)
                classes.add(currentClass);
            currentClass = currentClass.getSuperclass();
        }

        for (Class clasz : classes) {
            for (Field field : clasz.getDeclaredFields()) {
                try {
                    field.setAccessible(true);
                    var fieldDTO = field.get(source);
                    if (fieldDTO != null) {
                        field.set(mod, fieldDTO);
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
