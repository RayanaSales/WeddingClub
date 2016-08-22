package jsf_beans;

import entidades.Pessoa;
import entidades.Telefone;
import enumeracoes.TelefoneCategoria;
import excecao.ExcecaoNegocio;
import excecao.MensagemExcecao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolationException;
import servico.TelefoneServico;

@ManagedBean
@SessionScoped
public class TelefoneBean implements Serializable
{

    @EJB
    private TelefoneServico telefoneServico;

    public List<Telefone> telefones;

    public Telefone telefone;

    public TelefoneBean()
    {
        telefone = new Telefone();
    }

    public List<Telefone> buscarTelefonesCelulares()
    {
        return telefoneServico.listarTelefonesPorCategoria(TelefoneCategoria.celular);
    }

    public List<Telefone> buscarTelefonesFixos()
    {
        List<Telefone> fixos = new ArrayList<>();

        fixos.addAll(telefoneServico.listarTelefonesPorCategoria(TelefoneCategoria.residencial));
        fixos.addAll(telefoneServico.listarTelefonesPorCategoria(TelefoneCategoria.empresarial));

        return fixos;
    }

    public void salvar()
    {
        listar(); //atualize a minha lista

        //SE N TEM CATEGORIA, EH PQ EH UM CELULAR
        if (telefone.getCategoria() == null)
        {
            telefone.setCategoria(TelefoneCategoria.celular);
            Pessoa pessoa = telefone.getPessoa(); //se houver pessoa, sete a pessoa no telefone
            if (pessoa != null)
            {
                pessoa.setTelefones(telefones);
                telefone.setPessoa(pessoa);
            }
        }

        try
        {
            telefoneServico.salvar(telefone);
            adicionarMessagem(FacesMessage.SEVERITY_INFO, "Cadastro realizado com sucesso!");
        } catch (ExcecaoNegocio ex)
        {
            adicionarMessagem(FacesMessage.SEVERITY_WARN, ex.getMessage());
        } catch (EJBException ex)
        {
            if (ex.getCause() instanceof ConstraintViolationException)
            {
                MensagemExcecao mensagemExcecao = new MensagemExcecao(ex.getCause());
                adicionarMessagem(FacesMessage.SEVERITY_WARN, mensagemExcecao.getMensagem());
            }
        }
        telefone = new Telefone(); //renove a instancia, para o proximo elemento
    }

    public void editar(int id) throws ExcecaoNegocio
    {
        listar(); //atualize a minha lista      

        telefone.setId(id);
        if (telefone.getCategoria() == null)
        {
            telefone.setCategoria(TelefoneCategoria.celular);
            try
            {
                telefoneServico.atualizar(telefone);
                adicionarMessagem(FacesMessage.SEVERITY_INFO, "Alterado com sucesso!");
            } catch (ExcecaoNegocio ex)
            {
                adicionarMessagem(FacesMessage.SEVERITY_WARN, ex.getMessage());
            } catch (EJBException ex)
            {
                if (ex.getCause() instanceof ConstraintViolationException)
                {
                    MensagemExcecao mensagemExcecao = new MensagemExcecao(ex.getCause());
                    adicionarMessagem(FacesMessage.SEVERITY_WARN, mensagemExcecao.getMensagem());
                }
            }

            telefone = new Telefone();
        }
    }

    public void remover(Telefone tel)
    {
        listar(); //atualize a minha lista

        try
        {
            telefoneServico.remover(tel);
            adicionarMessagem(FacesMessage.SEVERITY_INFO, "Removido com sucesso!");
        } catch (ExcecaoNegocio ex)
        {
            adicionarMessagem(FacesMessage.SEVERITY_WARN, ex.getMessage());
        } catch (EJBException ex)
        {
            if (ex.getCause() instanceof ConstraintViolationException)
            {
                MensagemExcecao mensagemExcecao = new MensagemExcecao(ex.getCause());
                adicionarMessagem(FacesMessage.SEVERITY_WARN, mensagemExcecao.getMensagem());
            }
        }
    }

    public void listar()
    {
        telefones = telefoneServico.listar();
    }

    public List<Telefone> getTelefones()
    {
        listar(); //atualize a minha lista
        return telefones;
    }

    public Telefone getTelefone()
    {
        return telefone;
    }

    public void setTelefone(Telefone telefone)
    {
        this.telefone = telefone;
    }

    public void setTelefoneServico(TelefoneServico telefoneServico)
    {
        this.telefoneServico = telefoneServico;
    }

    public TelefoneCategoria[] getCategorias()
    {
        TelefoneCategoria[] categorias = new TelefoneCategoria[2];

        categorias[0] = TelefoneCategoria.empresarial;
        categorias[1] = TelefoneCategoria.residencial;

        return categorias;
    }

    protected void adicionarMessagem(FacesMessage.Severity severity, String mensagem)
    {       
        FacesMessage message = new FacesMessage(severity, mensagem, null);
        FacesContext.getCurrentInstance().addMessage(null, message);        
    }
}
