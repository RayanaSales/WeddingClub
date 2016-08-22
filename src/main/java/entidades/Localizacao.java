package entidades;

import javax.validation.constraints.NotNull;
import enumeracoes.EstadosDoBrasil;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@SequenceGenerator(name = "LOCALIZACAO_SEQUENCE", sequenceName = "LOCALIZACAO_SEQUENCE", allocationSize = 1, initialValue = 1)
public class Localizacao implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOCALIZACAO_SEQUENCE")
    private int id;

    @NotNull
    @Size(min = 3, max = 40)
    @Pattern(regexp = "[A-Za-z ]+", message = "{entidades.Localizacao.logradouro}")
    @Column(name = "txt_logradouro")
    private String logradouro; //chave secundaria
    
    @NotNull
    @Size(min = 3, max = 40)
    @Pattern(regexp = "[A-Za-z ]+", message = "{entidades.Localizacao.bairro}")
    @Column(name = "txt_bairro")
    private String bairro;
    
    @NotNull
    @Size(min = 3, max = 40)
    @Pattern(regexp = "[A-Za-z ]+", message = "{entidades.Localizacao.cidade}")
    @Column(name = "txt_cidade")
    private String cidade;
   
    @NotNull
    @Size(min = 3, max = 40)
    @Pattern(regexp = "[a-zA-Z0-9 ]+", message = "{entidades.Localizacao.complemento}")
    @Column(name = "txt_complemento")
    private String complemento;
    
    @NotNull
    @Size(min = 8, max = 8)
    @Pattern(regexp = "[0-9]+", message = "{entidades.Localizacao.cep}")
    @Column(name = "txt_cep")
    private String cep;
        
    @Column(name = "numero_numero")
    private int numero;

    @Enumerated(EnumType.STRING)
    EstadosDoBrasil estado;
    
    //one to one bidirecional
    @OneToOne(mappedBy = "localizacao", fetch = FetchType.EAGER)
    private Cerimonia cerimonia;
    
    //one to one bidirecional
    @OneToOne(mappedBy = "localizacao", fetch = FetchType.EAGER)
    private Loja loja;

    public Localizacao()
    {
    }

    public Localizacao(EstadosDoBrasil estado, String cidade, String bairro, String logradouro, String complemento, String cep, int numero)
    {
        this.estado = estado;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.complemento = complemento;
        this.cep = cep;
        this.numero = numero;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getRua()
    {
        return logradouro;
    }

    public void setRua(String logradouro)
    {
        this.logradouro = logradouro;
    }

    public String getBairro()
    {
        return bairro;
    }

    public void setBairro(String bairro)
    {
        this.bairro = bairro;
    }

    public String getCep()
    {
        return cep;
    }

    public void setCep(String cep)
    {
        this.cep = cep;
    }

    public int getNumero()
    {
        return numero;
    }

    public void setNumero(int numero)
    {
        this.numero = numero;
    }

    public String getLogradouro()
    {
        return logradouro;
    }

    public void setLogradouro(String logradouro)
    {
        this.logradouro = logradouro;
    }

    public String getCidade()
    {
        return cidade;
    }

    public void setCidade(String cidade)
    {
        this.cidade = cidade;
    }

    public String getComplemento()
    {
        return complemento;
    }

    public void setComplemento(String complemento)
    {
        this.complemento = complemento;
    }

    public EstadosDoBrasil getEstado()
    {
        return estado;
    }

    public void setEstado(EstadosDoBrasil estado)
    {
        this.estado = estado;
    }

    public Cerimonia getCerimonia()
    {
        return cerimonia;
    }

    public void setCerimonia(Cerimonia cerimonia)
    {
        this.cerimonia = cerimonia;
    }

    public Loja getLoja()
    {
        return loja;
    }

    public void setLoja(Loja loja)
    {
        this.loja = loja;
    }    
    
    public boolean associada()
    {
        if(loja == null && cerimonia == null)
            return false;
        return true;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (o != null)
        {
            if (o instanceof Localizacao)
            {
                Localizacao outra = (Localizacao) o;

                if (this.id == outra.getId())
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
        int hash = 5;
        hash = 53 * hash + this.id;
        return hash;
    }    
}