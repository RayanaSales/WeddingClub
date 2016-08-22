package servico;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

public class Servico
{
    @PersistenceContext(unitName = "casamento", type = PersistenceContextType.TRANSACTION)
    protected EntityManager em;
  
    public Servico()
    {
        
    }
}
