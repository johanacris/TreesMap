package Modelo;

import Modelo.PlagaCultivo;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-03T16:36:05")
@StaticMetamodel(Plaga.class)
public class Plaga_ { 

    public static volatile SingularAttribute<Plaga, String> tipoPlaga;
    public static volatile SingularAttribute<Plaga, Integer> idPlaga;
    public static volatile ListAttribute<Plaga, PlagaCultivo> plagaCultivoList;
    public static volatile SingularAttribute<Plaga, String> nombrePlaga;

}