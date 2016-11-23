package jsf_beans;

import criptografia.Encripta;
import entidades.Cerimonia;
import entidades.Convidado;
import entidades.Noivo;
import entidades.Pessoa;
import enumeracoes.ConvidadoCategoria;
import excecao.ExcecaoNegocio;
import excecao.MensagemExcecao;
import java.io.Serializable;
import java.util.ArrayList;
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
import servico.ConvidadoServico;

@ManagedBean
@SessionScoped
public class ConvidadoBean implements Serializable {

    @EJB
    private ConvidadoServico convidadoServico;

    public List<Convidado> convidados;
    public Convidado convidado;
    public Convidado convidadoLogado;
    Encripta encripta;

    public ConvidadoBean() {
        convidado = new Convidado();
        convidadoLogado = new Convidado();
        encripta = new Encripta();
    }

    public void listar() {
        
        Noivo noivoAtual = (Noivo) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");

        convidados = convidadoServico.listar(noivoAtual.getCerimonia());
    }

    public List<Convidado> getConvidados() {
        listar(); //atualize a minha lista
        return convidados;
    }

    public void salvar() {
        Noivo noivoAtual = (Noivo) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");

        // Quando é realizado o cadastro inicial no sistema
        if (noivoAtual != null) {

            listar(); //atualize a minha lista

            //setar o produtor, na lista de novasPessoas em cerimonia.
            Cerimonia cerimonia = noivoAtual.getCerimonia();
            List<Pessoa> novasPessoas = new ArrayList<>();
            novasPessoas.add(convidado);

            if (cerimonia != null) {
                cerimonia.setPessoas(novasPessoas);
            }
            convidado.setCerimonia(cerimonia);
        }
        else 
        {
            List<Pessoa> novasPessoas = new ArrayList<>();
            novasPessoas.add(convidado);

        }

            //criptografa senha
            String senha = convidado.getSenha();
            convidado.setNumeroAleatorio(encripta.Sorteia());
            senha = encripta.encriptar(senha, convidado.getNumeroAleatorio());
            convidado.setSenha(senha);

            try {
                convidadoServico.salvar(convidado);
                adicionarMessagem(FacesMessage.SEVERITY_INFO, "Cadastro realizado com sucesso!");
            } catch (ExcecaoNegocio ex) {
                adicionarMessagem(FacesMessage.SEVERITY_WARN, ex.getMessage());
            } catch (EJBException ex) {
                if (ex.getCause() instanceof ConstraintViolationException) {
                    MensagemExcecao mensagemExcecao = new MensagemExcecao(ex.getCause());
                    adicionarMessagem(FacesMessage.SEVERITY_WARN, mensagemExcecao.getMensagem());
                }
            }
        

        convidado = new Convidado(); //renove a instancia, para o proximo elemento
    }

    public void editar(RowEditEvent editEvent) {
        convidado = (Convidado) editEvent.getObject();
        editar(convidado.getId());
    }

    public void editar(int id) {
        listar(); //atualize a minha lista
        convidado.setId(id);

        try {
            convidadoServico.atualizar(convidado);
            adicionarMessagem(FacesMessage.SEVERITY_INFO, "Alterado com sucesso!");
        } catch (ExcecaoNegocio ex) {
            adicionarMessagem(FacesMessage.SEVERITY_WARN, ex.getMessage());
        } catch (EJBException ex) {
            if (ex.getCause() instanceof ConstraintViolationException) {
                MensagemExcecao mensagemExcecao = new MensagemExcecao(ex.getCause());
                adicionarMessagem(FacesMessage.SEVERITY_WARN, mensagemExcecao.getMensagem());
            }
        }

        convidado = new Convidado();
    }

    public void remover(Convidado convidado) {
        convidadoServico.remover(convidado);
        adicionarMessagem(FacesMessage.SEVERITY_INFO, "Removido com sucesso!");
    }

    protected void adicionarMessagem(FacesMessage.Severity severity, String mensagem) {
        FacesMessage message = new FacesMessage(severity, mensagem, "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public Convidado getConvidado() {
        return convidado;
    }

    public Convidado getConvidadoLogado() {
        convidadoLogado = (Convidado) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");
        return convidadoLogado;
    }

    public ConvidadoServico getConvidadoServico() {
        return convidadoServico;
    }

    public ConvidadoCategoria[] getCategorias() {
        return ConvidadoCategoria.values();
    }

    public void setConvidado(Convidado convidado) {
        this.convidado = convidado;
    }

    public void setConvidadoServico(ConvidadoServico convidadoServico) {
        this.convidadoServico = convidadoServico;
    }

    public boolean validaObjeto(Convidado c) {
        boolean valido = false;

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Convidado>> constraintViolations = validator.validate(c);
        if (constraintViolations.size() > 0) {
            Iterator<ConstraintViolation<Convidado>> iterator = constraintViolations.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<Convidado> cv = iterator.next();
                System.out.println(cv.getMessage());
                System.out.println(cv.getPropertyPath());
            }
        }

        if (constraintViolations.isEmpty()) {
            valido = true;
            System.out.println("LOCAL VALIDO");
        } else {
            System.out.println("LOCAL INVALIDOOOOOOOOOOOOOOOOOO");
        }

        return valido;
    }

    public void teste(RowEditEvent event) {
        System.out.println("aaaaaaaaaaaa");
        Convidado c = (Convidado) event.getObject();
        System.out.println("OBJETO: " + c.getNome());
    }

    public String verificaCerimoniaDoConvidadoLogado() {
        if (convidadoLogado.getCerimonia() == null) {
            return "Você ainda não pertence a nenhuma cerimônia!";
        } else {
            String cerimonia;

            cerimonia = convidadoLogado.getCerimonia().getLocalizacao() + " - " + convidadoLogado.getCerimonia().getDataHora();

            return cerimonia;
        }
    }

    public void AdicionarACerimonia() {

        Noivo noivoAtual = (Noivo) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");

        convidado.setCerimonia(noivoAtual.getCerimonia());

        try {
            convidadoServico.atualizar(convidado);
            adicionarMessagem(FacesMessage.SEVERITY_INFO, "Adicionado com sucesso!");
        } catch (ExcecaoNegocio ex) {
            adicionarMessagem(FacesMessage.SEVERITY_WARN, ex.getMessage());
        } catch (EJBException ex) {
            if (ex.getCause() instanceof ConstraintViolationException) {
                MensagemExcecao mensagemExcecao = new MensagemExcecao(ex.getCause());
                adicionarMessagem(FacesMessage.SEVERITY_WARN, mensagemExcecao.getMensagem());
            }
        }
    }

}
