package entidades;

import entidades.Buffet;
import entidades.Pessoa;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-12-15T19:46:29")
@StaticMetamodel(Cerimonia.class)
public class Cerimonia_ { 

    public static volatile SingularAttribute<Cerimonia, Integer> id;
    public static volatile SingularAttribute<Cerimonia, Buffet> buffet;
    public static volatile ListAttribute<Cerimonia, Pessoa> pessoas;
    public static volatile SingularAttribute<Cerimonia, Date> dataHora;
    public static volatile SingularAttribute<Cerimonia, String> localizacao;

}