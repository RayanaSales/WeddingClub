package teste;

import entidades.Buffet;
import entidades.Noivo;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import servico.GrupoServico;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Teste {
  
    public GrupoServico grupoServico;
    
    private static EntityManagerFactory emf;   
    private EntityManager em;
   
    @BeforeClass
    public static void setUpClass()
    {        
        emf = Persistence.createEntityManagerFactory("casamento");
    }

    @AfterClass
    public static void tearDownClass()
    {
        emf.close();
    }

    @Before
    public void setUp()
    {
       em = emf.createEntityManager();       
    }

    @After
    public void tearDown()
    {
            em.close();
    }
    
    
    public Teste() 
    {
    
    }
   
  
    @Test
    public void t01_buscarBuffet() throws Exception
    {
        Buffet b = new Buffet();
        b.setId(1);
        b.setValorTotalGasto(1000);
        em.persist(b);
        
        Buffet c = em.find(Buffet.class, 1);
        assertEquals(1, c.getId().longValue());
    }
    
    @Test
    public void t02_adicionarNoivo()
    {
        Noivo n = new Noivo();
        n.setCerimonia(null);
        n.setId(1);
        n.setEmail("noivo@gmail.com");
        n.setNome("Fulano");
        n.setSenha("123456");
        
        grupoServico.associarGrupo_UsuarioNoivo(n);
        grupoServico.associarGrupoNoivo(n);
        em.persist(n);
        
        n = em.find(Noivo.class, 1);
        
        assertEquals(1, n.getId().longValue());
     
    }
 
}
