package servico;

import entidades.Localizacao;
import excecao.ExcecaoNegocio;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.TypedQuery;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class LocalizacaoServico extends Servico
{
    public void salvar(Localizacao localizacao) throws ExcecaoNegocio
    {
        em.flush();
        if (existente(localizacao.getLogradouro()) == false)        
            em.persist(localizacao);
        else throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
    }

    public List<Localizacao> listar()
    {
        return em.createQuery("select t from Localizacao t", Localizacao.class).getResultList();
    }

    public void remover(Localizacao localizacao) throws ExcecaoNegocio
    {
        if(localizacao.associada() == false)
        {
        Localizacao t = (Localizacao) em.find(Localizacao.class, localizacao.getId()); //se n tiver isso, o jpa acha que n deatachou        
        em.remove(t);  
        }
        else throw new ExcecaoNegocio(ExcecaoNegocio.LOCAL_ASSOCIADO);
    }

    public void atualizar(Localizacao localizacao) throws ExcecaoNegocio
    {
       em.flush();
        if (existente(localizacao.getLogradouro()) == false)        
            em.merge(localizacao);
        else throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
    }

    public boolean existente(Localizacao localizacao)
    {
        em.flush();
        return listar().contains(localizacao);
    }

    private boolean existente(String logradouro)
    {
        TypedQuery<Localizacao> query;
        query = em.createQuery("select b from Localizacao b where b.logradouro like ?1", Localizacao.class);
        query.setParameter(1, logradouro);
        List<Localizacao> locais = query.getResultList();

        if (locais.isEmpty())
        {
            return false;
        }

        return true;
    }
}
