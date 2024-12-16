package br.edu.ifpe.CRMHealthLink.annontations;

import br.edu.ifpe.CRMHealthLink.domain.entity.ValidadorCPF;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidadorCPF.class) // Validator será implementado no próximo passo
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCPF {
    String message() default "CPF inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
