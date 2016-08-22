package entidades;

import enumeracoes.ConvidadoCategoria;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue(value = "C")
@PrimaryKeyJoinColumn(name = "id_pessoa", referencedColumnName = "id")
public class Convidado extends Pessoa implements Serializable
{
    @NotNull
    @Enumerated(EnumType.STRING)
    ConvidadoCategoria categoria;
      
    @NotNull    
    @Column(name = "numero_quantidadeSenhas")
    Integer quantidadeSenhas;
        
    public Convidado()
    {
      
    }

    public Convidado(ConvidadoCategoria cc, int quantidadeSenhas)
    {        
        categoria = cc;
        this.quantidadeSenhas = quantidadeSenhas;        
    }

    public Integer getQuantidadeSenhas()
    {
        return quantidadeSenhas;
    }

    public void setQuantidadeSenhas(Integer quantidadeSenhas)
    {
        this.quantidadeSenhas = quantidadeSenhas;
    }

    public ConvidadoCategoria getCategoria()
    {
        return categoria;
    }

    public void setCategoria(ConvidadoCategoria categoria)
    {
        this.categoria = categoria;
    }

    public void setId(int id)
    {
        this.id = id;
    }
    
     @Override
    public boolean equals(Object o)
    {
        if (o != null)
        {
            if (o instanceof Convidado)
            {
                Convidado outra = (Convidado) o;
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
