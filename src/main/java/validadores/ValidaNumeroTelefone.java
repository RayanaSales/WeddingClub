package validadores;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target( {ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidadorNumeroTelefone.class)
@Documented
public @interface ValidaNumeroTelefone
{
    String message() default "O número está incorreto. Se for telefone deve iniciar com 3, caso seja telefone deve começar com 9, 8, ou 7.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
