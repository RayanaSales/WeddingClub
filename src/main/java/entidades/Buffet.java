package entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity
@SequenceGenerator(name = "BUFFET_SEQUENCE", sequenceName = "BUFFET_SEQUENCE", allocationSize = 1, initialValue = 1)
public class Buffet implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BUFFET_SEQUENCE")
    private int id;

    @NotNull
    @validadores.ValidaPreco
    @Column(name = "numero_valorTotal")
    private double valorTotalGasto; //chave secundaria

    @OneToMany(mappedBy = "buffet", fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<ComesBebes> comesBebes;
    
    //one to one bidirecional
    @OneToOne(mappedBy = "buffet", fetch = FetchType.LAZY)
    private Cerimonia cerimonia;

    public Buffet()
    {
        comesBebes = new ArrayList<>();
    }

    public Buffet(double valorTotalGasto)
    {
        this.valorTotalGasto = valorTotalGasto;
        comesBebes = new ArrayList<>();
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public double getValorTotalGasto()
    {
        return valorTotalGasto;
    }

    public void setValorTotalGasto(double valorTotalGasto)
    {
        this.valorTotalGasto = valorTotalGasto;
    }

    public void setComesBebes(List<ComesBebes> ComesBebesNovos)
    {

        if (ComesBebesNovos != null && this.comesBebes != null)
        {
            for (ComesBebes comesBebe : ComesBebesNovos)
            {
                if (!comesBebes.contains(comesBebe))
                {
                    comesBebes.add(comesBebe);
                }
            }
        }

    }

    public List<ComesBebes> getComesBebes()
    {
        return comesBebes;
    }

    public Cerimonia getCerimonia()
    {
        return cerimonia;
    }

    public void setCerimonia(Cerimonia cerimonia)
    {
        this.cerimonia = cerimonia;
    }
        
    public boolean associado()
    {       
        //A CERIMONIA, E DOMINANTE, EU TB, POREM CERIMONIA, EH MAIS IMPORTANTE Q EU. ENTAO ELA ME EXCLUE.
        if(cerimonia == null) 
            return false;
        
        return true;       
    }

    @Override
    public boolean equals(Object o)
    {
        if (o != null)
        {
            if (o instanceof Buffet)
            {
                Buffet outra = (Buffet) o;

                if (Objects.equals(this.valorTotalGasto, outra.valorTotalGasto)) //confere atributos
                {                   
                    return true; //qd descomentar o de cima, tira essa linha
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 29 * hash + this.id;
        return hash;
    }
}
