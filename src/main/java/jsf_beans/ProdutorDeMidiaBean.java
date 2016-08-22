package jsf_beans;

import criptografia.Encripta;
import entidades.Cerimonia;
import entidades.Noivo;
import entidades.Pessoa;
import entidades.ProdutorDeMidia;
import enumeracoes.ProdutorDeMidiaCategoria;
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
import org.primefaces.event.RowEditEvent;
import servico.ProdutorDeMidiaServico;

@ManagedBean
@SessionScoped
public class ProdutorDeMidiaBean implements Serializable
{
    @EJB
    public ProdutorDeMidiaServico produtorServico;

    public List<ProdutorDeMidia> produtores;
    public ProdutorDeMidia produtor;
    public ProdutorDeMidia produtorLogado;
    Encripta encripta;

    public ProdutorDeMidiaBean()
    {
        produtor = new ProdutorDeMidia();
        produtorLogado = new ProdutorDeMidia();
        encripta = new Encripta();
    }

    protected void adicionarMessagem(FacesMessage.Severity severity, String mensagem)
    {
        FacesMessage message = new FacesMessage(severity, mensagem, "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void listar()
    {
        produtores = produtorServico.listar();
    }

    public void salvar()
    {
        listar(); //atualize a minha lista

        //seta o produto na cerimonia
        Cerimonia cerimonia = produtor.getCerimonia();
        List<Pessoa> novasPessoas = new ArrayList<>();
        novasPessoas.add(produtor);
        if (cerimonia != null)
        {
            cerimonia.setPessoas(novasPessoas);
        }
        produtor.setCerimonia(cerimonia);
        
        //criptografa senha
        String senha = produtor.getSenha();
        produtor.setNumeroAleatorio(encripta.Sorteia());
        senha = encripta.encriptar(senha, produtor.getNumeroAleatorio());
        produtor.setSenha(senha);

        try
        {
            produtorServico.salvar(produtor);
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

        produtor = new ProdutorDeMidia(); //renove a instancia, para o proximo elemento
    }

    public void editar(RowEditEvent editEvent) {
        produtor = (ProdutorDeMidia) editEvent.getObject();
        editar(produtor.getId());
    }
    
    public void editar(int id)
    {
        listar(); //atualize a minha lista

        produtor.setId(id);
        try
        {
            produtorServico.atualizar(produtor);
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

        produtor = new ProdutorDeMidia();
    }

    public void remover(ProdutorDeMidia produtor)
    {   
        produtorServico.remover(produtor);
        adicionarMessagem(FacesMessage.SEVERITY_INFO, "Removido com sucesso!");
    }

    public ProdutorDeMidiaServico getProdutorServico()
    {
        return produtorServico;
    }

    public List<ProdutorDeMidia> getProdutores()
    {
        listar(); //atualize a minha lista
        return produtores;
    }

    public ProdutorDeMidia getProdutor()
    {
        return produtor;
    }

    public ProdutorDeMidia getProdutorLogado() {
         produtorLogado = (ProdutorDeMidia) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");     
        return produtorLogado;
    }
    
    public void setProdutor(ProdutorDeMidia produtor)
    {
        this.produtor = produtor;
    }

    public ProdutorDeMidiaCategoria[] getCategorias()
    {
        return ProdutorDeMidiaCategoria.values();
    }
}
