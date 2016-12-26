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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@SequenceGenerator(name = "BUFFET_SEQUENCE", sequenceName = "BUFFET_SEQUENCE", allocationSize = 1, initialValue = 1)
public class Buffet implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BUFFET_SEQUENCE")
    private int id;
    
    @NotNull
    @Size(min = 3, max = 40)
    @Column(name = "txt_nome")
    private String nome;    
    
//    @validadores.ValidaPreco
//    @Column(name = "numero_valorTotal")
//    private double valorTotalGasto; //chave secundaria

    @OneToMany(mappedBy = "buffet", fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<ComesBebes> comesBebes;
    
    //one to one bidirecional
    @OneToOne(mappedBy = "buffet", fetch = FetchType.EAGER)
    private Cerimonia cerimonia;

    public Buffet()
    {
        comesBebes = new ArrayList<>();
    }

    public Buffet(double valorTotalGasto)
    {
        //this.valorTotalGasto = valorTotalGasto;
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
    
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

//    public double getValorTotalGasto()
//    {        
//        if(!comesBebes.isEmpty()){   
//            double valorTotalGasto = 0.0;
//            for(ComesBebes cb : comesBebes){
//                valorTotalGasto += cb.getValor();
//            }            
//            return valorTotalGasto;
//        }
//        
//        return 0.0;
//    }

//    public void setValorTotalGasto(double valorTotalGasto)
//    {
//        this.valorTotalGasto = valorTotalGasto;
//    }

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

                if (Objects.equals(this.nome, outra.nome)) //confere atributos
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
        int hash = 7;
        hash = 29 * hash + this.id;
        return hash;
    }

    
    
}
