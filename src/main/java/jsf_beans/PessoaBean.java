package jsf_beans;

import entidades.Pessoa;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import servico.PessoaServico;

@ManagedBean
@SessionScoped
public class PessoaBean implements Serializable
{
    @EJB
    private PessoaServico pessoaServico;
    
    public List<Pessoa> pessoas;
   // public Pessoa pessoa;

    public PessoaBean()
    {
      //  pessoa = new Pessoa();
    }
    
    public void listar()
    {
        pessoas = pessoaServico.listar();
    }

    public PessoaServico getPessoaServico()
    {
        return pessoaServico;
    }

    public void setPessoaServico(PessoaServico pessoaServico)
    {
        this.pessoaServico = pessoaServico;
    }

    public List<Pessoa> getPessoas()
    {
        listar();
        return pessoas;
    }
    
    public Pessoa buscarPessoa(String email)
    {
        return pessoaServico.buscarPessoa(email);
    }

}
