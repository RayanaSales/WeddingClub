package entidades;

import entidades.Cerimonia;
import entidades.Loja;
import enumeracoes.EstadosDoBrasil;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-24T21:15:25")
@StaticMetamodel(Localizacao.class)
public class Localizacao_ { 

    public static volatile SingularAttribute<Localizacao, Integer> id;
    public static volatile SingularAttribute<Localizacao, String> bairro;
    public static volatile SingularAttribute<Localizacao, String> cidade;
    public static volatile SingularAttribute<Localizacao, String> complemento;
    public static volatile SingularAttribute<Localizacao, String> cep;
    public static volatile SingularAttribute<Localizacao, EstadosDoBrasil> estado;
    public static volatile SingularAttribute<Localizacao, Loja> loja;
    public static volatile SingularAttribute<Localizacao, String> logradouro;
    public static volatile SingularAttribute<Localizacao, Cerimonia> cerimonia;
    public static volatile SingularAttribute<Localizacao, Integer> numero;

}