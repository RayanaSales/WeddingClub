package jsf_beans;

import entidades.Buffet;
import entidades.ComesBebes;
import enumeracoes.ComesBebesCategoria;
import excecao.ExcecaoNegocio;
import excecao.MensagemExcecao;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolationException;
import org.primefaces.event.RowEditEvent;
import servico.ComesBebesServico;

@ManagedBean
@SessionScoped
public class ComesBebesBean implements Serializable
{
    @EJB
    private ComesBebesServico comesBebesServico;

    public List<ComesBebes> cbs;
    public ComesBebes cb;

    public ComesBebesBean()
    {
        cb = new ComesBebes();
    }

    public void salvar()
    {
        listar(); //atualize a minha lista

        //sete no buffet, a lista de come e bebes
        Buffet b = cb.getBuffet();
        if (b != null)
        {
            b.setComesBebes(cbs);
            cb.setBuffet(b);
        }

        try
        {
            comesBebesServico.salvar(cb);
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

        cb = new ComesBebes(); //renove a instancia, para o proximo elemento        
    }

    public void editar(RowEditEvent editEvent) {
        cb = (ComesBebes) editEvent.getObject();
        editar(cb.getId());
    }
    
    public void editar(int id)
    {
        listar(); //atualize a minha lista   
        cb.setId(id);
        try
        {
            comesBebesServico.atualizar(cb);
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
        cb = new ComesBebes();
        listar();
    }

    public void remover(ComesBebes cb)
    {
        try
        {
            comesBebesServico.remover(cb);
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

    protected void adicionarMessagem(FacesMessage.Severity severity, String mensagem)
    {
        FacesMessage message = new FacesMessage(severity, mensagem, "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    @PostConstruct
    public void listar()
    {
        cbs = comesBebesServico.listar();
    }

    public List<ComesBebes> getCbs()
    {
        listar();
        return cbs;
    }

    public void setCbs(List<ComesBebes> comesBebes)
    {
        this.cbs = comesBebes;
    }

    public ComesBebes getCb()
    {
        return cb;
    }

    public void setCb(ComesBebes cb)
    {
        this.cb = cb;
    }

    public ComesBebesCategoria[] getCategorias()
    {
        return ComesBebesCategoria.values();
    }
}
