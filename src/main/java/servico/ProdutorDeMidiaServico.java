package servico;

import entidades.Cerimonia;
import entidades.ProdutorDeMidia;
import excecao.ExcecaoNegocio;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.TypedQuery;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProdutorDeMidiaServico extends Servico
{
    @EJB
    private GrupoServico grupoServico;
    
    public void salvar(ProdutorDeMidia produtor) throws ExcecaoNegocio
    {
        em.flush();
        if (existente(produtor.getEmail()) == false)
        {
            grupoServico.associarGrupo_UsuarioProdutor(produtor);
            grupoServico.associarGrupoProdutor(produtor);
            em.persist(produtor);
        } else
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }

    public List<ProdutorDeMidia> listar()
    {
        TypedQuery<ProdutorDeMidia> query = em.createQuery("SELECT c FROM ProdutorDeMidia c", ProdutorDeMidia.class);
        List<ProdutorDeMidia> produtores = query.getResultList();

        return produtores;
    }

    public void remover(ProdutorDeMidia produtor)
    {
        ProdutorDeMidia c = (ProdutorDeMidia) em.find(ProdutorDeMidia.class, produtor.getId()); //se n tiver isso, o jpa acha que n deatachou        
        em.remove(c);
    }

    public void atualizar(ProdutorDeMidia produtor) throws ExcecaoNegocio
    {
        em.flush();
        if (existente(produtor.getEmail()) == true)
        {
            em.merge(produtor);
        } else if (existente(produtor.getEmail()) == false)
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }

    public boolean existente(ProdutorDeMidia produtor)
    {
        em.flush();
        return listar().contains(produtor);
    }

    public List<ProdutorDeMidia> listarConvidadosDaCerimoniaAtual(Cerimonia c)
    {
        TypedQuery<ProdutorDeMidia> query = em.createQuery("SELECT c FROM ProdutorDeMidia c WHERE c.cerimonia = ?1", ProdutorDeMidia.class);
        query.setParameter(1, c);
        List<ProdutorDeMidia> produtor = query.getResultList();

        return produtor;
    }
    private boolean existente(String email)
    {
        TypedQuery<ProdutorDeMidia> query;
        query = em.createQuery("select b from Pessoa b where b.email like ?1", ProdutorDeMidia.class);
        query.setParameter(1, email);
        List<ProdutorDeMidia> produtores = query.getResultList();

        if (produtores.isEmpty())
        {
            return false;
        }

        return true;
    }
}
