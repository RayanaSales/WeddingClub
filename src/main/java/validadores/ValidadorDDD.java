package validadores;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidadorDDD implements ConstraintValidator<ValidaDDD, String>
{
    private List<String> ddds;
    
    //http://www.ddi-ddd.com.br/Codigos-Telefone-Brasil/
    
    @Override
    public void initialize(ValidaDDD validaEstado) {
        this.ddds = new ArrayList<>();
        this.ddds.add("68");
        this.ddds.add("82");
        this.ddds.add("96");
        this.ddds.add("97");
        this.ddds.add("92");
        this.ddds.add("77");
        this.ddds.add("75");
        this.ddds.add("73");
        this.ddds.add("74");
        this.ddds.add("88");
        this.ddds.add("85");
        this.ddds.add("28");
        this.ddds.add("27");
        this.ddds.add("61");
        this.ddds.add("62");
        this.ddds.add("64");
        this.ddds.add("99");
        this.ddds.add("98");
        this.ddds.add("65");
        this.ddds.add("66");
        this.ddds.add("67");
        this.ddds.add("31");
        this.ddds.add("32");
        this.ddds.add("33");
        this.ddds.add("34");
        this.ddds.add("35");
        this.ddds.add("37");        
        this.ddds.add("38");
        this.ddds.add("91");
        this.ddds.add("94");
        this.ddds.add("93");        
        this.ddds.add("83");        
        this.ddds.add("41");
        this.ddds.add("42");
        this.ddds.add("43");
        this.ddds.add("44");
        this.ddds.add("45");
        this.ddds.add("46");        
        this.ddds.add("81");
        this.ddds.add("87");
        this.ddds.add("31");
        this.ddds.add("86");
        this.ddds.add("89");        
        this.ddds.add("21");
        this.ddds.add("22");
        this.ddds.add("24");        
        this.ddds.add("84");         
        this.ddds.add("51");
        this.ddds.add("53");
        this.ddds.add("54");
        this.ddds.add("55");         
        this.ddds.add("69");         
        this.ddds.add("95");
        this.ddds.add("47");
        this.ddds.add("49");        
        this.ddds.add("48");        
        this.ddds.add("17");
        this.ddds.add("18");        
        this.ddds.add("19");
        this.ddds.add("11");
        this.ddds.add("12");
        this.ddds.add("14");
        this.ddds.add("15");        
        this.ddds.add("13");
        this.ddds.add("16");        
        this.ddds.add("79");
        this.ddds.add("63");
    }

    @Override
    public boolean isValid(String valor, ConstraintValidatorContext context) {
        return valor == null ? false : ddds.contains(valor);
    }
}
