package servico;

import entidades.Buffet;
import entidades.Cerimonia;
import entidades.ComesBebes;
import entidades.Convidado;
import excecao.ExcecaoNegocio;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class BuffetServico extends Servico {

    public void salvar(Buffet buffet) throws ExcecaoNegocio {

        em.persist(buffet);
    }

    public List<Buffet> listar(Cerimonia c) {
        em.flush();
        TypedQuery<Buffet> query = em.createQuery("select t from Buffet t WHERE t.cerimonia = ?1", Buffet.class);
        query.setParameter(1, c);

        return query.getResultList();
    }

    public List<Buffet> listarComComesBebes() {
        em.flush();
        return em.createQuery("select t from Buffet AS t JOIN FETCH t.comesBebes", Buffet.class).getResultList();
    }

    public void remover(Buffet buffet) throws ExcecaoNegocio {
        /* CERIMONIA E BUFFET, SAO DOMINANTES. E SE RELACIONAM. 
            A CERIMONIA EH MAIS IMPORTANTE QUE O BUFFET, LOGO CERIMONIA EXCLUE BUFFET.
            O BUFFET NÃO PODE DELETAR CERIMONIA. POR ISSO ESSA EXCECAO EH LANÇADA AQ.        
         */
        if (buffet.associado() == false) {
            Buffet t = (Buffet) em.find(Buffet.class, buffet.getId());
            em.remove(t);
        } else {
            throw new ExcecaoNegocio(ExcecaoNegocio.BUFFET_ASSOCIADO);
        }
    }

    public void atualizar(Buffet buffet) throws ExcecaoNegocio {
        em.flush();
        em.merge(buffet);

    }

    public Buffet buscar(int id) {
        return (Buffet) em.find(Buffet.class, id);        
    }

//    private boolean existente(double valor) {
//        TypedQuery<Buffet> query;
//        query = em.createQuery("select b from Buffet b where b.valorTotalGasto = ?1", Buffet.class);
//        query.setParameter(1, valor);
//        List<Buffet> buffets = query.getResultList();
//
//        if (buffets.isEmpty()) {
//            return false;
//        }
//        return true;
//    }
}
