package entidades;

import entidades.Buffet;
import enumeracoes.ComesBebesCategoria;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-12-26T20:46:22")
@StaticMetamodel(ComesBebes.class)
public class ComesBebes_ { 

    public static volatile SingularAttribute<ComesBebes, Integer> id;
    public static volatile SingularAttribute<ComesBebes, ComesBebesCategoria> categoria;
    public static volatile SingularAttribute<ComesBebes, Buffet> buffet;
    public static volatile SingularAttribute<ComesBebes, String> produto;
    public static volatile SingularAttribute<ComesBebes, Integer> quantidade;
    public static volatile SingularAttribute<ComesBebes, Double> valor;

}