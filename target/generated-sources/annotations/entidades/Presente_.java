package entidades;

import entidades.Cerimonia;
import entidades.Loja;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-24T21:15:25")
@StaticMetamodel(Presente.class)
public class Presente_ { 

    public static volatile SingularAttribute<Presente, Integer> id;
    public static volatile SingularAttribute<Presente, String> nome;
    public static volatile SingularAttribute<Presente, String> ondeEncontrar;
    public static volatile SingularAttribute<Presente, Cerimonia> cerimonia;
    public static volatile ListAttribute<Presente, Loja> lojas;
    public static volatile SingularAttribute<Presente, String> descricao;

}