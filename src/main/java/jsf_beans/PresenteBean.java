package jsf_beans;

import entidades.Cerimonia;
import entidades.Presente;
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
import servico.PresenteServico;

@ManagedBean
@SessionScoped
public class PresenteBean implements Serializable
{

    @EJB
    private PresenteServico presenteServico;

    public List<Presente> presentes;
    public Presente presente;

    public PresenteBean()
    {
        presente = new Presente();
    }

    public void setar()
    {

    }

    public void salvar()
    {
        listar(); //atualize a minha lista

        //seta os presentes la na cerimonia
        presentes.add(presente);
        Cerimonia c = presente.getCerimonia();
        if (c != null)
        {
            c.setPresentes(presentes);
            presente.setCerimonia(c);
        }

        try
        {
            presenteServico.salvar(presente);
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

        presente = new Presente(); //renove a instancia, para o proximo elemento
    }

    public void editar(int id)
    {
        listar(); //atualize a minha lista
        presente.setId(id);
        try
        {
            presenteServico.atualizar(presente);
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

        presente = new Presente();
    }

    public void remover(Presente presente)
    {
        try
        {
            presenteServico.remover(presente);
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
        presentes = presenteServico.listar();
    }

    public List<Presente> getPresentes()
    {
        listar(); //atualize a minha lista
        return presentes;
    }

    public Presente getPresente()
    {
        return presente;
    }

    public void setPresente(Presente presente)
    {
        this.presente = presente;
    }

    public void setPresenteServico(PresenteServico presenteServico)
    {
        this.presenteServico = presenteServico;
    }

    protected void adicionarMessagem(FacesMessage.Severity severity, String mensagem)
    {
        FacesMessage message = new FacesMessage(severity, mensagem, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
        System.out.println("DEU ERRO" + mensagem);
    }

//    public List<Loja> listarLojas()
//    {
//        return lojaServico.listar();
//    }
//
//    public List<Loja> listarLojasPresente(int id)
//    {
//        int idPresenteGeral;
//        List<Loja> lojasGeral = listarLojas();
//        List<Loja> lojasPresentes = new ArrayList<>();
//
//        for (int i = 0; i < lojasGeral.size(); i++)
//        {
//            idPresenteGeral = lojasGeral.get(i).getPresente().getId();
//
//            if (idPresenteGeral == id)
//            {
//                lojasPresentes.add(lojasGeral.get(i));
//            }
//        }
//        return lojasPresentes;
//    }
}
