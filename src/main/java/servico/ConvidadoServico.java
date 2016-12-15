package servico;

import entidades.Cerimonia;
import entidades.Convidado;
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
public class ConvidadoServico extends Servico
{
    @EJB
    private GrupoServico grupoServico;
    
    public void salvar(Convidado convidado) throws ExcecaoNegocio
    {
        em.flush();
        if (existente(convidado.getEmail()) == false)
        {
            grupoServico.associarGrupo_UsuarioConvidado(convidado);
            grupoServico.associarGrupoConvidado(convidado);
            em.persist(convidado);
        } else
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }

    public List<Convidado> listarConvidadosDaCerimoniaAtual(Cerimonia c)
    {
        TypedQuery<Convidado> query = em.createQuery("SELECT c FROM Convidado c WHERE c.cerimonia = ?1", Convidado.class);
        query.setParameter(1, c);
        List<Convidado> convidado = query.getResultList();

        return convidado;
    }

    public List<Convidado> listar()
    {
        TypedQuery<Convidado> query = em.createQuery("SELECT c FROM Convidado c", Convidado.class);
        List<Convidado> convidado = query.getResultList();

        return convidado;
    }
    
    public void remover(Convidado convidado)
    {
        Convidado c = (Convidado) em.find(Convidado.class, convidado.getId()); //se n tiver isso, o jpa acha que n deatachou        
        em.remove(c);
    }

    public void atualizar(Convidado convidado) throws ExcecaoNegocio
    {
        em.flush();
        if (existente(convidado.getEmail()) == true)
        {
            em.merge(convidado);
        } else if (existente(convidado.getEmail()) == false)
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }

    public boolean existente(Convidado convidado)
    {
        em.flush();
        return listar().contains(convidado);
    }

    private boolean existente(String email)
    {
        TypedQuery<Convidado> query;
        query = em.createQuery("select b from Pessoa b where b.email like ?1", Convidado.class);
        query.setParameter(1, email);
        List<Convidado> pessoas = query.getResultList();

        if (pessoas.isEmpty())
        {
            return false;
        }

        return true;
    }
}
