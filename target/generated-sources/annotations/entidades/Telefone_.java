package entidades;

import entidades.Loja;
import entidades.Pessoa;
import enumeracoes.TelefoneCategoria;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-24T21:15:25")
@StaticMetamodel(Telefone.class)
public class Telefone_ { 

    public static volatile SingularAttribute<Telefone, Integer> id;
    public static volatile SingularAttribute<Telefone, TelefoneCategoria> categoria;
    public static volatile SingularAttribute<Telefone, String> ddd;
    public static volatile SingularAttribute<Telefone, Loja> loja;
    public static volatile SingularAttribute<Telefone, Pessoa> pessoa;
    public static volatile SingularAttribute<Telefone, String> numero;

}