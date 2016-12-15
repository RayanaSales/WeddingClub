package tests;

import entidades.Buffet;
import entidades.Noivo;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jsf_beans.NoivoBean;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import servico.GrupoServico;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JUnitTests {
    
    //public GrupoServico grupoServico;    
    
    public JUnitTests() {
    }
     
    
    @Test
    public void t01_adicionarNoivo()
    {
        NoivoBean nb = new NoivoBean();
        
        nb.noivo.setCerimonia(null);
        nb.noivo.setId(1);
        nb.noivo.setEmail("noivo@gmail.com");
        nb.noivo.setNome("Fulano");
        nb.noivo.setSenha("123456");        
//        grupoServico = new GrupoServico();
//        grupoServico.associarGrupo_UsuarioNoivo(nb.noivo);
//        grupoServico.associarGrupoNoivo(nb.noivo);
        
        assertEquals(1, nb.noivo.getId().longValue());
     
    }
}
