package validadores;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidadorPreco implements ConstraintValidator<ValidaPreco, Double>
{

    @Override
    public void initialize(ValidaPreco a)
    {

    }

    @Override
    public boolean isValid(Double valor, ConstraintValidatorContext cvc)
    {
        boolean valido = false;

        if (valor != null)
        {
            if (valor > 0.0) //o cara pode ter escolhido n botar o preço ainda
            {
                valido = true;
            }
        }

        return valido;
    }

}
