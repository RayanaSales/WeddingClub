package entidades;

import entidades.Cerimonia;
import entidades.ComesBebes;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-12-28T10:21:39")
@StaticMetamodel(Buffet.class)
public class Buffet_ { 

    public static volatile SingularAttribute<Buffet, Integer> id;
    public static volatile ListAttribute<Buffet, ComesBebes> comesBebes;
    public static volatile SingularAttribute<Buffet, String> nome;
    public static volatile SingularAttribute<Buffet, Cerimonia> cerimonia;

}