package validadores;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidadorURL implements ConstraintValidator<ValidaURL, String>
{

    @Override
    public void initialize(ValidaURL a)
    {
    }

    @Override
    public boolean isValid(String link, ConstraintValidatorContext cvc)
    {
        boolean valido = false;

        if (link != null) //pq o cara n quis botar o site dele
        {
            if (link.startsWith("www.") && (link.endsWith(".com") || link.endsWith(".com.br") || link.endsWith(".net")))
            {
                valido = true;
            }
        }
        else valido = true;
        
        return valido;
    }
}
