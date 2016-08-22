package servico;

import entidades.Presente;
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
public class PresenteServico extends Servico
{
    public void salvar(Presente presente) throws ExcecaoNegocio 
    {
        em.flush();
        if(existente(presente.getNome()) == false)
            em.persist(presente);
        else throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
    }

    public List<Presente> listar()
    {
        return em.createQuery("select p from Presente p", Presente.class).getResultList();
    }

    public void remover(Presente presente) throws ExcecaoNegocio
    {
        if(presente.associado() == false)
        {
        Presente t = (Presente) em.find(Presente.class, presente.getId()); //se n tiver isso, o jpa acha que n deatachou        
        em.remove(t);            
        }
        else throw new ExcecaoNegocio(ExcecaoNegocio.PRESENTE_ASSOCIADO);
    }

    public void atualizar(Presente presente) throws ExcecaoNegocio
    {              
       em.flush();
        if(existente(presente.getNome()) == false)
            em.merge(presente);
        else throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
    }
    
    public Presente buscar(int id)
    {
        return (Presente) em.find(Presente.class, id);
    }
    
    private boolean existente(String nome)
    {       
        TypedQuery<Presente> query;
        query = em.createQuery("select b from Presente b where b.nome like ?1", Presente.class);
        query.setParameter(1, nome);
        List<Presente> presentes = query.getResultList();

        if (presentes.isEmpty())
        {
            return false;
        }

        return true;
    }
}
