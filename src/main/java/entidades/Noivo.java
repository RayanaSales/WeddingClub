package entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;

@Entity
@DiscriminatorValue(value = "N")
@PrimaryKeyJoinColumn(name = "id_pessoa", referencedColumnName = "id")
@SequenceGenerator(name = "PESSOA_SEQUENCE", sequenceName = "PESSOA_SEQUENCE", allocationSize = 1, initialValue = 1)
public class Noivo extends Pessoa implements Serializable
{

    @OneToMany(mappedBy = "noivo", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoupaDosNoivos> roupaDosNoivos;

    public Noivo()
    {
        roupaDosNoivos = new ArrayList<>();
    }

    public List<RoupaDosNoivos> getRoupaDosNoivos()
    {
        return roupaDosNoivos;
    }

    public void setRoupaDosNoivos(List<RoupaDosNoivos> roupaDosNoivosNovas)
    {

        if (roupaDosNoivos == null)
        {
            roupaDosNoivos = new ArrayList<>();
        }
        if (roupaDosNoivosNovas == null)
        {
            roupaDosNoivosNovas = new ArrayList<>();
        }

        for (RoupaDosNoivos roupa : roupaDosNoivosNovas)
        {

            if (!roupaDosNoivos.contains(roupa))
            {
                roupaDosNoivos.add(roupa);
            }
        }
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
