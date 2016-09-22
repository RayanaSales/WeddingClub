package entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "disc_pessoa", discriminatorType = DiscriminatorType.STRING, length = 1)
@SequenceGenerator(name = "PESSOA_SEQUENCE", sequenceName = "PESSOA_SEQUENCE", allocationSize = 1, initialValue = 1)
public class Pessoa implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PESSOA_SEQUENCE")
    protected int id;

    @NotNull
    @Size(min = 3, max = 40)
    @Pattern(regexp = "[A-Za-z ]+", message = "{entidades.Pessoa.nome}")
    @Column(name = "txt_nome")
    protected String nome;
    
    @Size(min = 5, max = 40)
    @Email
    @Column(name = "txt_email")
    protected String email; //chave secundaria
    
    @NotNull       
    @Column(name = "txt_senha")
    private String senha; 
    
    @Column(name = "numero_numeroAleatorio")
    private int numeroAleatorio;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cerimonia", referencedColumnName = "id")
    protected Cerimonia cerimonia;
        
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "pessoa_grupo", joinColumns = @JoinColumn(name = "id_pessoa"),
            inverseJoinColumns = @JoinColumn(name = "id_grupo"))    
    public List<Grupo> grupos;

    public Pessoa()
    {        
        grupos = new ArrayList<>();
    }

    public Pessoa(String nome, String email, Cerimonia cerimonia)
    {
        this.nome = nome;
        this.email = email;
        this.cerimonia = cerimonia;        
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getSenha()
    {
        return senha;
    }

    public void setSenha(String senha)
    {
        this.senha = senha;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public int getNumeroAleatorio()
    {
        return numeroAleatorio;
    }

    public void setNumeroAleatorio(int numeroAleatorio)
    {
        this.numeroAleatorio = numeroAleatorio;
    }
    
    public Cerimonia getCerimonia()
    {
        return cerimonia;
    }

    public void setCerimonia(Cerimonia cerimonia)
    {
        this.cerimonia = cerimonia;
    }

    public List<Grupo> getGrupos()
    {
        return grupos;
    }

    public void setGrupos(List<Grupo> novosGrupos)
    {
        if (novosGrupos != null)
        {
            for (Grupo grupo : novosGrupos)
            {
                if (!grupos.contains(grupo))
                {
                    grupos.add(grupo);
                }
            }
        }
    }

    @Override
    public boolean equals(Object o)
    {
        boolean valido = false;

        if (o != null)
        {
            if (o instanceof ProdutorDeMidia)
            {
                ProdutorDeMidia outra = (ProdutorDeMidia) o;
                if (this.id == outra.id && this.nome.equals(outra.nome))
                {
                    valido = true;
                }
            } else if (o instanceof Noivo)
            {
                Noivo outra = (Noivo) o;
                if (this.id == outra.id && this.nome.equals(outra.nome))
                {
                    valido = true;
                }
            } else if (o instanceof Convidado)
            {
                Convidado outra = (Convidado) o;
                if (this.id == outra.id && this.nome.equals(outra.nome))
                {
                    valido = true;
                }
            }
        }
        return valido;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 53 * hash + this.id;
        return hash;
    }
}
