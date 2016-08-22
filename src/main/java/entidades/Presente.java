package entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@SequenceGenerator(name = "PRESENTE_SEQUENCE", sequenceName = "PRESENTE_SEQUENCE", allocationSize = 1, initialValue = 1)
public class Presente implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRESENTE_SEQUENCE")
    private int id;

    @NotNull
    @Pattern(regexp = "[A-Za-z ]+", message = "{entidades.Presente.nome}")
    @Column(name = "txt_nome")
    private String nome;

    @NotNull
    @Size(max = 50)
    @Pattern(regexp = "[A-Za-z ]+", message = "{entidades.Presente.descricao}")
    @Column(name = "txt_descricao")
    private String descricao;

    @Size(max = 50)
    @Pattern(regexp = "[A-Za-z ]+", message = "{entidades.Presente.ondeEncontrar}")
    @Column(name = "txt_ondeEncontrar")
    private String ondeEncontrar;

    //uma cerimonia contem uma lista de presentes
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_cerimonia", referencedColumnName = "id")
    private Cerimonia cerimonia;

    //Um presente pode ser encontrado em uma lista de lojas
    @OneToMany(mappedBy = "presente", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Loja> lojas;

    public Presente()
    {
    }

    public Presente(Cerimonia c, String nome, String descricao, String ondeEncontrar)
    {
        this.cerimonia = c;
        this.nome = nome;
        this.descricao = descricao;
        this.ondeEncontrar = ondeEncontrar;

        lojas = new ArrayList<>();
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

    public String getDescricao()
    {
        return descricao;
    }

    public void setDescricao(String descricao)
    {
        this.descricao = descricao;
    }

    public String getOndeEncontrar()
    {
        return ondeEncontrar;
    }

    public void setOndeEncontrar(String ondeEncontrar)
    {
        this.ondeEncontrar = ondeEncontrar;
    }

    public Cerimonia getCerimonia()
    {
        return cerimonia;
    }

    public void setCerimonia(Cerimonia cerimonia)
    {
        this.cerimonia = cerimonia;
    }

    //PADRAO EXPERT
    public void setLojas(List<Loja> novaslojasOndeEncontrar)
    {
        if (novaslojasOndeEncontrar != null)
        {

            for (Loja loja : novaslojasOndeEncontrar)
            {
                if (!lojas.contains(loja))
                {
                    lojas.add(loja);
                }
            }
        }
    }

    public List<Loja> getLojas()
    {
        return lojas;
    }
    
    public boolean associado()
    {
        if(cerimonia == null && lojas.isEmpty())
            return false;
        return true;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o != null)
        {
            if (o instanceof Presente)
            {
                Presente outra = (Presente) o;
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
