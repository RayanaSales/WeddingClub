package entidades;

import enumeracoes.TelefoneCategoria;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@SequenceGenerator(name = "TELEFONE_SEQUENCE", sequenceName = "TELEFONE_SEQUENCE", allocationSize = 1, initialValue = 1)
public class Telefone implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TELEFONE_SEQUENCE")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column
    TelefoneCategoria categoria;

    @NotNull
    @Size(min = 2, max = 2)
    @validadores.ValidaDDD
    @Column(name = "txt_ddd")
    private String ddd;

    @NotNull
    @Size(min = 8, max = 9)
    @validadores.ValidaNumeroTelefone
    @Column(name = "txt_numero")
    private String numero;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id")
    private Pessoa pessoa;
    
    //one to one bidirecional
    @OneToOne(mappedBy = "telefone", fetch = FetchType.EAGER)
    private Loja loja;

    public Telefone()
    {
    }

    public Telefone(TelefoneCategoria categoria, String ddd, String numero) //usado para loja
    {
        this.categoria = categoria;
        this.ddd = ddd;
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

    public Loja getLoja()
    {
        return loja;
    }

    public void setLoja(Loja loja)
    {
        this.loja = loja;
    }
    
    public TelefoneCategoria getCategoria()
    {
        return categoria;
    }

    public void setCategoria(TelefoneCategoria categoria)
    {
        this.categoria = categoria;
    }

    public String getDdd()
    {
        return ddd;
    }

    public void setDdd(String ddd)
    {
        this.ddd = ddd;
    }

    public String getNumero()
    {
        return numero;
    }

    public void setNumero(String numero)
    {
        this.numero = numero;
    }

    public Pessoa getPessoa()
    {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa)
    {
        this.pessoa = pessoa;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o != null)
        {
            if (o instanceof Telefone)
            {
                Telefone outra = (Telefone) o;
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
        int hash = 5;
        hash = 71 * hash + this.id;
        return hash;
    }
    
    public boolean associado()
    {       
        if(pessoa == null && loja == null)
            return false;
        
        return true;       
    }
}
