package entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@SequenceGenerator(name = "GRUPO_SEQUENCE", sequenceName = "GRUPO_SEQUENCE", allocationSize = 1, initialValue = 1)
public class Grupo implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GRUPO_SEQUENCE")
    private int id;
    
    @NotNull
    @Size(min = 3)
    @Pattern(regexp = "[A-Za-z ]+", message = "{entidades.Loja.nome}")
    @Column(name = "txt_nome")
    private String nome;
    
    @ManyToMany(mappedBy = "grupos")            
    List<Pessoa> pessoas;

    public Grupo()
    {
        pessoas = new ArrayList<>();
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public List<Pessoa> getPessoas()
    {
       
        return pessoas;
    }

    //padrao expert
    public void setPessoas(List<Pessoa> novasPessoas)
    {
        if (novasPessoas != null)
        {
            for (Pessoa pessoa : novasPessoas)
            {
                if (!pessoas.contains(pessoa))
                {
                    pessoas.add(pessoa);
                }
            }
        }
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (o != null)
        {
            if (o instanceof Grupo)
            {
                Grupo outra = (Grupo) o;
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
        int hash = 7;
        hash = 89 * hash + this.id;
        return hash;
    }
    
}
