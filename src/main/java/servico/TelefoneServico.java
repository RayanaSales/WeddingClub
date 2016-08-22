package servico;

import entidades.Telefone;
import enumeracoes.TelefoneCategoria;
import excecao.ExcecaoNegocio;
import java.util.ArrayList;
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
public class TelefoneServico extends Servico
{
    public void salvar(Telefone telefone) throws ExcecaoNegocio
    {
        em.flush();
        if (existente(telefone.getNumero()) == false)
        {
            em.persist(telefone);
        } else
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }

    public List<Telefone> listarTelefonesPorCategoria(TelefoneCategoria categoria)
    {
        List<Telefone> telefonesTotais = listar();
        List<Telefone> telefonesEmpresariais = new ArrayList<>();

        for (Telefone telefone : telefonesTotais)
        {
            if (telefone.getCategoria() == categoria)
            {
                telefonesEmpresariais.add(telefone);
            }
        }
        return telefonesEmpresariais;
    }

    public List<Telefone> listar()
    {
        return em.createQuery("select t from Telefone t", Telefone.class).getResultList();
    }

    public void remover(Telefone telefone) throws ExcecaoNegocio
    {
        if (telefone.associado() == false)
        {
            Telefone t = (Telefone) em.find(Telefone.class, telefone.getId()); //se n tiver isso, o jpa acha que n deatachou        
            em.remove(t);
        } else
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.TELEFONE_ASSOCIADO);
        }
    }

    public void atualizar(Telefone telefone) throws ExcecaoNegocio
    {
        em.flush();
        if (existente(telefone.getNumero()) == false)
        {
            em.merge(telefone);
        } else
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }

    private boolean existente(String numero)
    {
        TypedQuery<Telefone> query;
        query = em.createQuery("select b from Telefone b where b.numero like ?1", Telefone.class);
        query.setParameter(1, numero);
        List<Telefone> telefones = query.getResultList();

        if (telefones.isEmpty())
        {
            return false;
        }

        return true;
    }
}
