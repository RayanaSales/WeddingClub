package entidades;

import enumeracoes.ConvidadoCategoria;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-12-28T10:21:39")
@StaticMetamodel(Convidado.class)
public class Convidado_ extends Pessoa_ {

    public static volatile SingularAttribute<Convidado, ConvidadoCategoria> categoria;
    public static volatile SingularAttribute<Convidado, Integer> quantidadeSenhas;

}