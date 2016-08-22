package entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

@Entity
@SequenceGenerator(name = "CERIMONIA_SEQUENCE", sequenceName = "CERIMONIA_SEQUENCE", allocationSize = 1, initialValue = 1)
public class Cerimonia implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CERIMONIA_SEQUENCE")
    private int id;

    @NotNull
    @Future
    @Column(name = "dt_dataHora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora; //chave secundaria

    //Relacionamentos:    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "id_localizacao", referencedColumnName = "id")
    private Localizacao localizacao;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "id_buffet", referencedColumnName = "id")
    private Buffet buffet;

    //lista de presentes que o casal cria
    @OneToMany(mappedBy = "cerimonia", fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<Presente> presentes;

    //uma cerimonia to many pessoas (noivos, convidados, produtorDeMidia)
    @OneToMany(mappedBy = "cerimonia", fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<Pessoa> pessoas;

    public Cerimonia()
    {
        presentes = new ArrayList<>();
        pessoas = new ArrayList<>();
    }

    public Cerimonia(Date dataHora)
    {
        this.dataHora = dataHora;
        //  this.localizacao = localizacao;        
    }

    public Date getDataHora()
    {
        return dataHora;
    }

    public void setDataHora(Date dataHora)
    {
        this.dataHora = dataHora;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Localizacao getLocalizacao()
    {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao)
    {
        this.localizacao = localizacao;
    }

    public Buffet getBuffet()
    {
        return buffet;
    }

    public void setBuffet(Buffet buffet)
    {
        this.buffet = buffet;
    }

    public List<Presente> getPresentes()
    {
        return presentes;
    }

    public void setPresentes(List<Presente> presentesNovos)
    {
        if (presentes == null)
        {
            presentes = new ArrayList<>();
        }
        if (presentesNovos == null)
        {
            presentesNovos = new ArrayList<>();
        }

        for (Presente presente : presentesNovos)
        {
            if (!presentes.contains(presente))
            {
                presentes.add(presente);
            }
        }
    }

    public List<Pessoa> getPessoas()
    {
        return pessoas;
    }

    public void setPessoas(List<Pessoa> pessoasNovas)
    {
        if (pessoas == null)
        {
            pessoas = new ArrayList<>();
        }
        
        if (pessoasNovas == null)
        {
            pessoasNovas = new ArrayList<>();
        }

        for (Pessoa pessoa : pessoasNovas)
        {
            if (!pessoas.contains(pessoa))
            {
                pessoas.add(pessoa);
            }
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (o != null)
        {
            if (o instanceof Cerimonia)
            {
                Cerimonia outra = (Cerimonia) o;
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
        hash = 67 * hash + this.id;
        return hash;
    }
}
