package entidades;

import entidades.Cerimonia;
import entidades.Grupo;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-11-02T21:48:03")
@StaticMetamodel(Pessoa.class)
public class Pessoa_ { 

    public static volatile SingularAttribute<Pessoa, Integer> id;
    public static volatile SingularAttribute<Pessoa, String> email;
    public static volatile SingularAttribute<Pessoa, String> nome;
    public static volatile SingularAttribute<Pessoa, Cerimonia> cerimonia;
    public static volatile SingularAttribute<Pessoa, String> senha;
    public static volatile SingularAttribute<Pessoa, Integer> numeroAleatorio;
    public static volatile ListAttribute<Pessoa, Grupo> grupos;

}