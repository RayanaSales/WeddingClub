package entidades;

import entidades.Buffet;
import entidades.Loja;
import enumeracoes.ComesBebesCategoria;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-24T21:15:25")
@StaticMetamodel(ComesBebes.class)
public class ComesBebes_ { 

    public static volatile SingularAttribute<ComesBebes, Integer> id;
    public static volatile SingularAttribute<ComesBebes, ComesBebesCategoria> categoria;
    public static volatile SingularAttribute<ComesBebes, Buffet> buffet;
    public static volatile SingularAttribute<ComesBebes, String> produto;
    public static volatile SingularAttribute<ComesBebes, Integer> quantidade;
    public static volatile SingularAttribute<ComesBebes, Double> valor;
    public static volatile SingularAttribute<ComesBebes, Loja> loja;

}