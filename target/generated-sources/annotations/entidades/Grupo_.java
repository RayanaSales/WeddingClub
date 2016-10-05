package entidades;

import entidades.Pessoa;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-10-05T09:53:04")
@StaticMetamodel(Grupo.class)
public class Grupo_ { 

    public static volatile SingularAttribute<Grupo, Integer> id;
    public static volatile ListAttribute<Grupo, Pessoa> pessoas;
    public static volatile SingularAttribute<Grupo, String> nome;

}