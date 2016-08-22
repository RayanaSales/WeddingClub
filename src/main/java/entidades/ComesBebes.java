package entidades;

import enumeracoes.ComesBebesCategoria;
import java.io.Serializable;
import javax.persistence.CascadeType;
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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@SequenceGenerator(name = "COMESBEBES_SEQUENCE", sequenceName = "COMESBEBES_SEQUENCE", allocationSize = 1, initialValue = 1)
public class ComesBebes implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMESBEBES_SEQUENCE")
    private int id;

    @NotNull
    @Column(name = "txt_produto")
    @Size(min = 3, max = 40)
    @Pattern(regexp = "[A-Za-z ]+", message = "{entidades.ComesBebes.produto}")
    private String produto; //chave secundaria
    
    @NotNull
    @Column(name = "numero_quantidade")
    private int quantidade;

    @NotNull
    @validadores.ValidaPreco
    @Column(name = "numero_valor")
    private double valor;

    @NotNull
    @Enumerated(EnumType.STRING)
    ComesBebesCategoria categoria;
      
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_buffet", referencedColumnName = "id")
    private Buffet buffet;

    //quero pedir o brigadeiro da minha vizinha, os doces finos de tal lugar, mas eu gosto da coxinha da padaria. Pede tudo de cada lugar
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "id_loja", referencedColumnName = "id")
    private Loja loja;

    public ComesBebes()
    {

    }

    public ComesBebes(Buffet buffet, String produto, Loja loja, ComesBebesCategoria categoria, int quantidade, double valor)
    {

        this.quantidade = quantidade;
        this.produto = produto;
        this.valor = valor;
        this.loja = loja;
        this.buffet = buffet;
        this.categoria = categoria;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getQuantidade()
    {
        return quantidade;
    }

    public void setQuantidade(int quantidade)
    {
        this.quantidade = quantidade;
    }

    public ComesBebesCategoria getCategoria()
    {
        return categoria;
    }

    public void setCategoria(ComesBebesCategoria categoria)
    {
        this.categoria = categoria;
    }

    public Buffet getBuffet()
    {
        return buffet;
    }

    public void setBuffet(Buffet buffet)
    {
        this.buffet = buffet;
    }

    public Loja getLoja()
    {
        return loja;
    }

    public void setLoja(Loja loja)
    {
        this.loja = loja;
    }

    public double getValor()
    {
        return valor;
    }

    public void setValor(double valor)
    {
        this.valor = valor;
    }

    public String getProduto()
    {
        return produto;
    }

    public void setProduto(String produto)
    {
        this.produto = produto;
    }
    
    public boolean associado()
    {
        if(buffet == null)
            return false;
        return true;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o != null)
        {
            if (o instanceof ComesBebes)
            {
                ComesBebes outra = (ComesBebes) o;
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
        hash = 53 * hash + this.id;
        return hash;
    }    
}
