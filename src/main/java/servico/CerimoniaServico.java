package servico;

import entidades.Cerimonia;
import excecao.ExcecaoNegocio;
import java.util.Date;
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
public class CerimoniaServico extends Servico
{
    public void salvar(Cerimonia cerimonia) throws ExcecaoNegocio
    {
        em.flush();
        if(existente(cerimonia.getDataHora()) == false)
            em.persist(cerimonia);
        else throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
    }

    public List<Cerimonia> listar()
    {     
        TypedQuery<Cerimonia> query = em.createQuery("SELECT c FROM Cerimonia c", Cerimonia.class);
        List<Cerimonia> cerimonias = query.getResultList();
        
        return cerimonias;
    }
    
    public void remover(Cerimonia cerimonia)
    {
        Cerimonia c = (Cerimonia) em.find(Cerimonia.class, cerimonia.getId()); //se n tiver isso, o jpa acha que n deatachou        
        em.remove(c);        
    }

    public void atualizar(Cerimonia cerimonia) throws ExcecaoNegocio
    {
       em.flush();
        if(existente(cerimonia.getDataHora()) == false)
            em.merge(cerimonia);
        else throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
    }

    private boolean existente(Date dataHora)
    {        
        TypedQuery<Cerimonia> query;
        query = em.createQuery("select b from Cerimonia b where b.dataHora = ?1", Cerimonia.class);
        query.setParameter(1, dataHora);
        List<Cerimonia> cerimonias = query.getResultList();

        if (cerimonias.isEmpty())
        {
            return false;
        }

        return true;
    }
}
