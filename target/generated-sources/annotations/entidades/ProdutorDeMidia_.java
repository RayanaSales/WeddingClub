package entidades;

import enumeracoes.ProdutorDeMidiaCategoria;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-24T21:15:25")
@StaticMetamodel(ProdutorDeMidia.class)
public class ProdutorDeMidia_ extends Pessoa_ {

    public static volatile SingularAttribute<ProdutorDeMidia, ProdutorDeMidiaCategoria> categoria;
    public static volatile SingularAttribute<ProdutorDeMidia, Double> preco;
    public static volatile SingularAttribute<ProdutorDeMidia, String> linkParaRedeSocial;

}