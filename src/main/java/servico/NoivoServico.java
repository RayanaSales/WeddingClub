package servico;

import entidades.Noivo;
import excecao.ExcecaoNegocio;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import static javax.ejb.TransactionManagementType.CONTAINER;
import javax.persistence.TypedQuery;

@Stateless
@TransactionManagement(CONTAINER)
public class NoivoServico extends Servico
{

    @EJB
    private GrupoServico grupoServico;

    public void salvar(Noivo noivo) throws ExcecaoNegocio
    {
        if (existente(noivo.getEmail()) == false)
        {
            grupoServico.associarGrupo_UsuarioNoivo(noivo);
            grupoServico.associarGrupoNoivo(noivo);
            em.persist(noivo);
        } else
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }

    public List<Noivo> listar()
    {
        return em.createQuery("select t from Noivo t", Noivo.class).getResultList();
    }

    public void remover(Noivo noivo)
    {
        Noivo t = (Noivo) em.find(Noivo.class, noivo.getId()); //se n tiver isso, o jpa acha que n deatachou        
        em.remove(t);
    }

    public void atualizar(Noivo noivo) throws ExcecaoNegocio
    {
        em.flush();
        if (existente(noivo.getEmail()) == true)
        {
            em.merge(noivo);
        } else if (existente(noivo.getEmail()) == false)
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }

    public boolean existente(String email)
    {
        TypedQuery<Noivo> query;
        query = em.createQuery("select b from Pessoa b where b.email like ?1", Noivo.class);
        query.setParameter(1, email);
        List<Noivo> pessoas = query.getResultList();

        if (pessoas.isEmpty())
        {
            return false;
        }

        return true;
    }
}
