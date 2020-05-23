package Modelo;

import Modelo.Cultivo;
import Modelo.Plaga;
import Modelo.PlagaCultivoPK;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-03T16:36:05")
@StaticMetamodel(PlagaCultivo.class)
public class PlagaCultivo_ { 

    public static volatile SingularAttribute<PlagaCultivo, Cultivo> cultivo;
    public static volatile SingularAttribute<PlagaCultivo, Plaga> plaga;
    public static volatile SingularAttribute<PlagaCultivo, Date> fechaAplicacion;
    public static volatile SingularAttribute<PlagaCultivo, String> anotacion;
    public static volatile SingularAttribute<PlagaCultivo, PlagaCultivoPK> plagaCultivoPK;

}