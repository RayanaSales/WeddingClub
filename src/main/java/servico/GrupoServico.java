package servico;

import entidades.Convidado;
import entidades.Grupo;
import entidades.Noivo;
import entidades.Pessoa;
import entidades.ProdutorDeMidia;
import java.util.ArrayList;
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
public class GrupoServico extends Servico
{
    @EJB
    PessoaServico pessoaServico;
    
    public void associarGrupo_UsuarioNoivo(Noivo pessoa)
    {
        List<Grupo> gruposDaPessoa = new ArrayList<>();
        Grupo grupoPessoa = buscar(4);
        gruposDaPessoa.add(grupoPessoa);
        
        pessoa.setGrupos(gruposDaPessoa);     
        em.merge(pessoa);
    }
    
    public void associarGrupo_UsuarioProdutor(ProdutorDeMidia pessoa)
    {
        List<Grupo> gruposDaPessoa = new ArrayList<>();
        Grupo grupoPessoa = buscar(4);
        gruposDaPessoa.add(grupoPessoa);
        
        pessoa.setGrupos(gruposDaPessoa);     
        em.merge(pessoa);
    }
    
    public void associarGrupo_UsuarioConvidado(Convidado pessoa)
    {
        List<Grupo> gruposDaPessoa = new ArrayList<>();
        Grupo grupoPessoa = buscar(4);
        gruposDaPessoa.add(grupoPessoa);
        
        pessoa.setGrupos(gruposDaPessoa);     
        em.merge(pessoa);
    }
        
    public void associarGrupoNoivo(Noivo pessoa)
    {
        List<Grupo> gruposDaPessoa = new ArrayList<>();
        Grupo grupoPessoa = buscar(1);
        gruposDaPessoa.add(grupoPessoa);
        
        pessoa.setGrupos(gruposDaPessoa);     
        em.merge(pessoa);
    }
    
    public void associarGrupoConvidado(Convidado pessoa)
    {
        List<Grupo> gruposDaPessoa = new ArrayList<>();
        Grupo grupoPessoa = buscar(3);
        gruposDaPessoa.add(grupoPessoa);
        
        pessoa.setGrupos(gruposDaPessoa);     
        em.merge(pessoa);
    }
    
    public void associarGrupoProdutor(ProdutorDeMidia pessoa)
    {
        List<Grupo> gruposDaPessoa = new ArrayList<>();
        Grupo grupoPessoa = buscar(2);
        gruposDaPessoa.add(grupoPessoa);
        
        pessoa.setGrupos(gruposDaPessoa);     
        em.merge(pessoa);
    }

    private boolean existente(String nome)
    {
        TypedQuery<Grupo> query;
        query = em.createQuery("select b from Grupo b where b.nome like ?1", Grupo.class);
        query.setParameter(1, nome);
        List<Grupo> grupos = query.getResultList();

        if (grupos.isEmpty())
        {
            return false;
        }

        return true;
    }
    
    public String buscarGrupoDaPessoa(String email)
    {
        String grupoAtual = "nenhum";
        Pessoa pessoa = pessoaServico.buscarPessoa(email);        
        List<Grupo> grupos = pessoa.getGrupos();
        
        for(Grupo grupo : grupos)
        {
            if(grupo.getNome().equals("noivo"))
                grupoAtual = "noivo";
            else if(grupo.getNome().equals("produtoDeMidia"))
                grupoAtual = "produtoDeMidia";
            else if(grupo.getNome().equals("convidado"))
                grupoAtual = "convidado";
        }
        return grupoAtual;
    }

    public List<Grupo> listar()
    {
        return em.createQuery("select g from Grupo AS g", Grupo.class).getResultList();
    }

    public Grupo buscar(int id)
    {
        return em.find(Grupo.class, id);
    }
}
