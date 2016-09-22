package entidades;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;

@Entity
@DiscriminatorValue(value = "N")
@PrimaryKeyJoinColumn(name = "id_pessoa", referencedColumnName = "id")
@SequenceGenerator(name = "PESSOA_SEQUENCE", sequenceName = "PESSOA_SEQUENCE", allocationSize = 1, initialValue = 1)
public class Noivo extends Pessoa implements Serializable
{

    public Noivo()
    {
       
    }
  
    @Override
    public boolean equals(Object o)
    {
        if (o != null)
        {
            if (o instanceof Noivo)
            {
                Noivo outra = (Noivo) o;
                if (this.id == outra.id)
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }
}
