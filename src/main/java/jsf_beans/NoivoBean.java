package jsf_beans;

import criptografia.Encripta;
import entidades.Cerimonia;
import entidades.Grupo;
import entidades.Noivo;
import entidades.Pessoa;
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
import javax.servlet.ServletException;
import javax.validation.ConstraintViolationException;
import org.primefaces.event.RowEditEvent;
import servico.NoivoServico;

@ManagedBean
@SessionScoped
public class NoivoBean implements Serializable
{
    @EJB
    private NoivoServico noivoServico;

    public List<Noivo> noivos;
    public Noivo noivo;
    public Noivo noivoLogado;
    public Grupo grupo;
    
    Encripta encripta;

    public NoivoBean()
    {        
        noivo = new Noivo();
        noivoLogado = new Noivo();
        encripta = new Encripta();
    }
 
    public void listar()
    {
        noivos = noivoServico.listar();
    }

    public void salvar()
    {
        listar(); //atualize a minha lista

        //seta o noivo na cerimonia
        Cerimonia cerimonia = noivo.getCerimonia();
        if (cerimonia != null)
        {
            List<Pessoa> novasPessoas = new ArrayList<>();
            novasPessoas.add(noivo);
            cerimonia.setPessoas(novasPessoas);
        }
        noivo.setCerimonia(cerimonia);
      
        //criptografa senha
        String senha = noivo.getSenha();
        noivo.setNumeroAleatorio(encripta.Sorteia());
        senha = encripta.encriptar(senha, noivo.getNumeroAleatorio());
        noivo.setSenha(senha);

        try
        {
            noivoServico.salvar(noivo);
            adicionarMessagem(FacesMessage.SEVERITY_INFO, "Cadastro realizado com sucesso!");
        } catch (ExcecaoNegocio ex)
        {
            adicionarMessagem(FacesMessage.SEVERITY_WARN, ex.getMessage());
        } catch (EJBException ex)
        {
            ex.getCause();
            if (ex.getCause() instanceof ConstraintViolationException)
            {
                MensagemExcecao mensagemExcecao = new MensagemExcecao(ex.getCause());
                adicionarMessagem(FacesMessage.SEVERITY_WARN, mensagemExcecao.getMensagem());
            }
        }

        noivo = new Noivo(); //renove a instancia, para o proximo elemento
    }
    
     public void editar(RowEditEvent editEvent) {
        noivo = (Noivo) editEvent.getObject();
        editar(noivo.getId());
    }

    public void editar(int id)
    {
       // listar(); //atualize a minha lista
        noivo.setId(id);
        try
        {
            noivoServico.atualizar(noivo);
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

        noivo = new Noivo();
    }

    public void remover(Noivo noivo) throws ServletException
    {
        noivoServico.remover(noivo);
        adicionarMessagem(FacesMessage.SEVERITY_INFO, "Removido com sucesso!");
        
        LoginBean loginBean = new LoginBean();
        loginBean.logout();
    }

    public NoivoServico getNoivoServico()
    {
        return noivoServico;
    }

    public void setNoivoServico(NoivoServico noivoServico)
    {
        this.noivoServico = noivoServico;
    }

    public List<Noivo> getNoivos()
    {
        listar(); //atualize a minha lista
        return noivos;
    }

    public void setNoivos(List<Noivo> noivos)
    {
        this.noivos = noivos;
    }

    public Noivo getNoivo()
    {
         return noivo;
    }

    public Noivo getNoivoLogado() {
        noivoLogado = (Noivo) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");    
        return noivoLogado;
    } 

    public void setNoivo(Noivo noivo)
    {
        this.noivo = noivo;
    }

    public Grupo getGrupo()
    {
        grupo = new Grupo();
        return grupo;
    }

    public void setGrupo(Grupo grupo)
    {
        this.grupo = grupo;
    }

    protected void adicionarMessagem(FacesMessage.Severity severity, String mensagem)
    {
        FacesMessage message = new FacesMessage(severity, mensagem, "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
