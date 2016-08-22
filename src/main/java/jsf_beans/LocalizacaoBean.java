package jsf_beans;

import entidades.Localizacao;
import enumeracoes.EstadosDoBrasil;
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
import servico.LocalizacaoServico;

@ManagedBean
@SessionScoped
public class LocalizacaoBean implements Serializable
{

    @EJB
    private LocalizacaoServico localizacaoServico;

    public List<Localizacao> locais;
    public Localizacao localizacao;

    public LocalizacaoBean()
    {
        localizacao = new Localizacao();
    }

    public void listar()
    {
        locais = localizacaoServico.listar();
    }

    public List<Localizacao> getLocais()
    {
        listar(); //atualize a minha lista
        return locais;
    }

    public void salvar()
    {
        if (validaObjeto(localizacao) == true)
        {
            listar(); //atualize a minha lista

            try
            {
                localizacaoServico.salvar(localizacao);
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

            localizacao = new Localizacao(); //renove a instancia, para o proximo elemento

        } else
        {
            adicionarMessagem(FacesMessage.SEVERITY_INFO, "Objeto invalido");
        }
    }

    public void editar(int id)
    {
        listar(); //atualize a minha lista
        localizacao.setId(id);
        try
        {
            localizacaoServico.atualizar(localizacao);
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

        localizacao = new Localizacao();
    }

    public void remover(Localizacao tel)
    {
        try
        {
            localizacaoServico.remover(tel);
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

    public Localizacao getLocalizacao()
    {
        return localizacao;
    }

    public LocalizacaoServico getLocalizacaoServico()
    {
        return localizacaoServico;
    }

    public EstadosDoBrasil[] getEstados()
    {
        return EstadosDoBrasil.values();
    }

    public void setLocalizacao(Localizacao localizacao)
    {
        this.localizacao = localizacao;
    }

    public void setLocalizacaoServico(LocalizacaoServico localizacaoServico)
    {
        this.localizacaoServico = localizacaoServico;
    }

    public boolean validaObjeto(Localizacao c)
    {
        boolean valido = false;

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Localizacao>> constraintViolations = validator.validate(c);
        if (constraintViolations.size() > 0)
        {
            Iterator<ConstraintViolation<Localizacao>> iterator = constraintViolations.iterator();
            while (iterator.hasNext())
            {
                ConstraintViolation<Localizacao> cv = iterator.next();
                System.out.println(cv.getMessage());
                System.out.println(cv.getPropertyPath());
            }
        }

        if (constraintViolations.isEmpty())
        {
            valido = true;
            System.out.println("LOCAL VALIDO");
        } else
        {
            System.out.println("LOCAL INVALIDOOOOOOOOOOOOOOOOOO");
        }

        return valido;
    }
}
