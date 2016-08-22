package validadores;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidadorNumeroTelefone implements ConstraintValidator<ValidaNumeroTelefone, String>
{    
    @Override
    public boolean isValid(String valor, ConstraintValidatorContext context) 
    {
        boolean valido = false;
        
        if(valor.startsWith("9") || valor.startsWith("8") || valor.startsWith("7") || valor.startsWith("3"))
            valido = true;
        
        return valido;
    }

    @Override
    public void initialize(ValidaNumeroTelefone a)
    {
       
    }
}
