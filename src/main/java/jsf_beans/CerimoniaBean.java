package jsf_beans;

import entidades.Cerimonia;
import entidades.Noivo;
import excecao.ExcecaoNegocio;
import excecao.MensagemExcecao;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.primefaces.event.RowEditEvent;
import servico.CerimoniaServico;

@ManagedBean
@SessionScoped
public class CerimoniaBean implements Serializable {

    @EJB
    public CerimoniaServico cerimoniaServico;

    public List<Cerimonia> cerimonias;
    public Cerimonia cerimonia;

    public CerimoniaBean() {
        cerimonia = new Cerimonia();
    }

    protected void adicionarMessagem(FacesMessage.Severity severity, String mensagem) {
        FacesMessage message = new FacesMessage(severity, mensagem, "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void salvar() {
        Noivo noivoAtual = (Noivo) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");

        if (noivoAtual.getCerimonia() == null) {
            if (validaObjeto(cerimonia) == true) {
                try {
                    cerimoniaServico.salvar(cerimonia);
                    cerimoniaServico.atualizarNoivo((Noivo) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado"), cerimonia);
                    adicionarMessagem(FacesMessage.SEVERITY_INFO, "Cadastro realizado com sucesso!");
                } catch (ExcecaoNegocio ex) {
                    adicionarMessagem(FacesMessage.SEVERITY_WARN, ex.getMessage());
                } catch (EJBException ex) {
                    if (ex.getCause() instanceof ConstraintViolationException) {
                        MensagemExcecao mensagemExcecao = new MensagemExcecao(ex.getCause());
                        adicionarMessagem(FacesMessage.SEVERITY_WARN, mensagemExcecao.getMensagem());
                    }
                }
                cerimonia = new Cerimonia(); //renove a instancia, para o proximo elemento

            } else {
                adicionarMessagem(FacesMessage.SEVERITY_INFO, "Objeto invalido");
            }
        } else {
            adicionarMessagem(FacesMessage.SEVERITY_INFO, "O noivo já possue uma cerimônia!");
        }
    }

    public void editar(RowEditEvent editEvent) {
        cerimonia = (Cerimonia) editEvent.getObject();
        editar(cerimonia.getId());
    }

    public void editar(int id) {
        listar(); //atualize a minha lista
        cerimonia.setId(id);
        try {
            cerimoniaServico.atualizar(cerimonia);
            adicionarMessagem(FacesMessage.SEVERITY_INFO, "Alterado com sucesso!");
        } catch (ExcecaoNegocio ex) {
            adicionarMessagem(FacesMessage.SEVERITY_WARN, ex.getMessage());
        } catch (EJBException ex) {
            if (ex.getCause() instanceof ConstraintViolationException) {
                MensagemExcecao mensagemExcecao = new MensagemExcecao(ex.getCause());
                adicionarMessagem(FacesMessage.SEVERITY_WARN, mensagemExcecao.getMensagem());
            }
        }

        cerimonia = new Cerimonia();
    }

    public void remover(Cerimonia cerimonia) {
        cerimoniaServico.remover(cerimonia);
        adicionarMessagem(FacesMessage.SEVERITY_INFO, "Removido com sucesso!");
    }

    public void listar() {
        
        cerimonias = cerimoniaServico.listar();
    }

    public Cerimonia cerimoniaAtual() {
        return cerimoniaServico.RetornaCerimoniaAtual();
    }

    public List<Cerimonia> getCerimonias() {
        listar(); //atualize a minha lista

        return cerimonias;
    }

    public Cerimonia getCerimonia() {
        return cerimonia;
    }

    public CerimoniaServico getCerimoniaServico() {
        return cerimoniaServico;
    }

    public void setCerimonia(Cerimonia cerimonia) {
        this.cerimonia = cerimonia;
    }

    public boolean validaObjeto(Cerimonia c) {
        boolean valido = false;

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Cerimonia>> constraintViolations = validator.validate(c);
        if (constraintViolations.size() > 0) {
            Iterator<ConstraintViolation<Cerimonia>> iterator = constraintViolations.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<Cerimonia> cv = iterator.next();
                System.out.println(cv.getMessage());
                System.out.println(cv.getPropertyPath());
            }
        }

        if (constraintViolations.isEmpty()) {
            valido = true;
        }

        return valido;
    }
}
