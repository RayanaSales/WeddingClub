package jsf_beans;

import entidades.Noivo;
import entidades.RoupaDosNoivos;
import excecao.ExcecaoNegocio;
import excecao.MensagemExcecao;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolationException;
import servico.NoivoServico;
import servico.RoupaDosNoivosServico;

@ManagedBean
@SessionScoped
public class RoupaDosNoivosBean implements Serializable
{

    @EJB
    private RoupaDosNoivosServico roupaServico;

    @EJB
    private NoivoServico noivoServico;

    public List<RoupaDosNoivos> roupas;
    public RoupaDosNoivos roupa;

    public RoupaDosNoivosBean()
    {
        roupa = new RoupaDosNoivos();
    }

    public void salvar()
    {
        listar(); //atualize a minha lista

        //seta em noivo , a lista de roupas
        roupas.add(roupa);
        Noivo n = roupa.getNoivo();
        if (n != null)
        {
            n.setRoupaDosNoivos(roupas);
            roupa.setNoivo(n);
        }

        try
        {
            roupaServico.salvar(roupa);
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
        roupa = new RoupaDosNoivos(); //renove a instancia, para o proximo elemento
    }

    public void editar(int id)
    {
        listar(); //atualize a minha lista      

        roupa.setId(id);
        try
        {
            roupaServico.atualizar(roupa);
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

        roupa = new RoupaDosNoivos();
    }

    public void remover(RoupaDosNoivos roupa)
    {
        try
        {
            roupaServico.remover(roupa);
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
        roupas = roupaServico.listar();
    }

    public List<RoupaDosNoivos> getRoupas()
    {
        listar(); //atualize a minha lista
        return roupas;
    }

    public RoupaDosNoivos getRoupa()
    {
        return roupa;
    }

    public RoupaDosNoivosServico getRoupaDosNoivosServico()
    {
        return roupaServico;
    }

    public void setRoupaDosNoivos(RoupaDosNoivos roupa)
    {
        this.roupa = roupa;
    }

    public void setRoupaDosNoivosServico(RoupaDosNoivosServico roupaServico)
    {
        this.roupaServico = roupaServico;
    }

    protected void adicionarMessagem(FacesMessage.Severity severity, String mensagem)
    {
        FacesMessage message = new FacesMessage(severity, mensagem, "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public List<Noivo> listarNoivos()
    {
        return noivoServico.listar();
    }
}
