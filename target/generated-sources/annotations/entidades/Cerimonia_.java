package entidades;

import entidades.Buffet;
import entidades.Localizacao;
import entidades.Pessoa;
import entidades.Presente;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-24T21:15:25")
@StaticMetamodel(Cerimonia.class)
public class Cerimonia_ { 

    public static volatile SingularAttribute<Cerimonia, Integer> id;
    public static volatile SingularAttribute<Cerimonia, Buffet> buffet;
    public static volatile ListAttribute<Cerimonia, Pessoa> pessoas;
    public static volatile SingularAttribute<Cerimonia, Date> dataHora;
    public static volatile SingularAttribute<Cerimonia, Localizacao> localizacao;
    public static volatile ListAttribute<Cerimonia, Presente> presentes;

}