package servico;

import entidades.Loja;
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
public class LojaServico extends Servico
{
    public void salvar(Loja loja) throws ExcecaoNegocio
    {       
        em.flush();
        if (existente(loja.getCnpj()) == false)
        {
            em.persist(loja);
        } else
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }

    public List<Loja> listar()
    {
        TypedQuery<Loja> query = em.createQuery("SELECT c FROM Loja c", Loja.class);
        List<Loja> lojas = query.getResultList();

        return lojas;
    }

    public void remover(Loja loja)
    {
        Loja c = (Loja) em.find(Loja.class, loja.getId()); //se n tiver isso, o jpa acha que n deatachou        
        em.remove(c);
    }

    public void atualizar(Loja loja) throws ExcecaoNegocio
    {
        em.flush();
        if (existente(loja.getCnpj()) == false)
        {
            em.merge(loja);
        } else
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }

        /*
        
        try
        {
            TypedQuery query = em.createQuery("SELECT c FROM Loja c WHERE c.cnpj = ?1 ", Loja.class);
            query.setParameter(1, loja.getCnpj());
            Loja l = (Loja) query.getSingleResult();

            return false;
        } catch (Exception e)
        {
            em.flush();
            em.merge(loja);
            return true;
        }
         */
    }

    private boolean existente(String cnpj)
    {
        TypedQuery<Loja> query;
        query = em.createQuery("select b from Loja b where b.cnpj like ?1", Loja.class);
        query.setParameter(1, cnpj);
        List<Loja> lojas = query.getResultList();

        if (lojas.isEmpty())
        {
            return false;
        }

        return true;
    }
}
