package entidades;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@SequenceGenerator(name = "ROUPADOSNOIVOS_SEQUENCE", sequenceName = "ROUPADOSNOIVOS_SEQUENCE", allocationSize = 1, initialValue = 1)
public class RoupaDosNoivos implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROUPADOSNOIVOS_SEQUENCE")
    private int id;
        
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id")
    private Noivo noivo;
    
    @NotNull
    @Size(min = 3, max = 40)
    @Pattern(regexp = "[A-Za-z ]+", message = "Deve possuir apenas letras, no maximo 1 maiuscula. Minimo:3 Max: 40.")
    @Column(name = "txt_roupa")
    private String roupa; //chave secundaria
    
    @NotNull
    @validadores.ValidaPreco
    @Column(name = "numero_valor")
    private double valor;
    
    
    public RoupaDosNoivos()
    {
    
    }
    
    public RoupaDosNoivos( Noivo noivo, String roupa, double valor)
    {
        this.noivo = noivo;
        this.roupa = roupa;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Noivo getNoivo() {
        return noivo;
    }

    public void setNoivo(Noivo noivo) {
        this.noivo = noivo;
    }
    
    

    public String getRoupa()
    {
        return roupa;
    }

    public void setRoupa(String roupa)
    {
        this.roupa = roupa;
    }  

    public double getValor()
    {
        return valor;
    }

    public void setValor(double valor)
    {
        this.valor = valor;
    }
    
    public boolean associado()
    {
        if(noivo == null)
            return false;
        
        return true;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (o != null)
        {
            if (o instanceof RoupaDosNoivos)
            {
                RoupaDosNoivos outra = (RoupaDosNoivos) o;
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
        int hash = 3;
        hash = 23 * hash + this.id;
        return hash;
    }
}
