package servico;

import entidades.Grupo;
import entidades.Pessoa;
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
public class PessoaServico extends Servico
{

    public List<Pessoa> listar()
    {
        return em.createQuery("select p from Pessoa p", Pessoa.class).getResultList();
    }

    public Pessoa buscarPessoa(String email)
    {
        TypedQuery<Pessoa> query = em.createQuery(
                "SELECT c FROM Pessoa c WHERE c.email like ?1", Pessoa.class);
        query.setParameter(1, email);
        
        return query.getSingleResult();        
    }
}
