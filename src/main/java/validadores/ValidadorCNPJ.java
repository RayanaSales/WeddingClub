/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validadores;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author natalia
 */
public class ValidadorCNPJ implements ConstraintValidator<ValidaCNPJ, String> {

    @Override
    public void initialize(ValidaCNPJ validaCNPJ) {

    }

    @Override
    public boolean isValid(String cnpj, ConstraintValidatorContext context) {

        return !(cnpj.equals("00000000000000") || cnpj.equals("11111111111111")
                || cnpj.equals("22222222222222") || cnpj.equals("33333333333333")
                || cnpj.equals("44444444444444") || cnpj.equals("55555555555555")
                || cnpj.equals("66666666666666") || cnpj.equals("77777777777777")
                || cnpj.equals("88888888888888") || cnpj.equals("99999999999999"));
    
    }
}
