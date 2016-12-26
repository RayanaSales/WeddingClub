package jsf_beans;

import entidades.Buffet;
import entidades.Cerimonia;
import entidades.ComesBebes;
import entidades.Convidado;
import entidades.Noivo;
import excecao.ExcecaoNegocio;
import excecao.MensagemExcecao;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolationException;
import org.primefaces.event.RowEditEvent;
import servico.BuffetServico;
import servico.CerimoniaServico;
import servico.ComesBebesServico;

@ManagedBean
@SessionScoped
public class BuffetBean implements Serializable
{
    @EJB
    private BuffetServico buffetServico;
    
    @EJB
    private ComesBebesServico comesBebesServico;

    @EJB
    private CerimoniaServico cerimoniaServico;

    private List<Buffet> buffets;

    private Buffet buffet;

    public BuffetBean()
    {
        buffet = new Buffet();
    }

    public void salvar()
    {
        
        listar();
        Noivo noivoAtual = (Noivo) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");

        try
        {
           
            buffetServico.salvar(buffet);
            
            // Atualizar o id do buffet da cerimonia
             noivoAtual.getCerimonia().setBuffet(buffet);
            cerimoniaServico.atualizar(noivoAtual.getCerimonia());
            
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

        //atualize a minha lista
        buffet = new Buffet(); //renove a instancia, para o proximo elemento        
    }

    public void editar(RowEditEvent editEvent) {        
        buffet = (Buffet) editEvent.getObject();
        editar(buffet.getId());
    }
    
    public void editar(int id)
    {
        listar(); //atualize a minha lista   
        buffet.setId(id);
        try
        {
            buffetServico.atualizar(buffet);
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

        buffet = new Buffet();
    }

    public void remover(Buffet buffet)
    {
        try
        {
            buffetServico.remover(buffet);
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

    public void setBuffetServico(BuffetServico buffetServico)
    {
        this.buffetServico = buffetServico;
    }

    public void listar()
    {
        Noivo noivoAtual = (Noivo) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");
        buffets = buffetServico.listar(noivoAtual.getCerimonia());
    }

    public List<Buffet> getBuffets()
    {
        listar();
        return buffets;
    }

    public void setBuffets(List<Buffet> buffets)
    {
        this.buffets = buffets;
    }

    public Buffet getBuffet()
    {
        return buffet;
    }

    public void setBuffet(Buffet buffet)
    {
        this.buffet = buffet;
    }

    private boolean algumaCerimoniaMeTem(Buffet buffet)
    {
        List<Cerimonia> cerimonias = cerimoniaServico.listar();

        for (Cerimonia cerimonia : cerimonias)
        {
            if (cerimonia.getBuffet().getId().equals(buffet.getId()))
            {
                return true;
            }
        }
        return false;
    }
        
    public double calcularValorTotalGasto(int idBuffet){
        List<ComesBebes> cbs = comesBebesServico.listarTodosBuffet(idBuffet);
        
        double valorTotal = 0.0;
        for(ComesBebes cb : cbs){
            valorTotal += cb.getQuantidade() * cb.getValor();
        }
        return valorTotal;
    }
}
