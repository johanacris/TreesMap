package Modelo;

import Modelo.Cultivo;
import Modelo.Fertilizante;
import Modelo.FertilizanteCultivoPK;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-03T16:36:05")
@StaticMetamodel(FertilizanteCultivo.class)
public class FertilizanteCultivo_ { 

    public static volatile SingularAttribute<FertilizanteCultivo, Cultivo> cultivo;
    public static volatile SingularAttribute<FertilizanteCultivo, Fertilizante> fertilizante;
    public static volatile SingularAttribute<FertilizanteCultivo, FertilizanteCultivoPK> fertilizanteCultivoPK;
    public static volatile SingularAttribute<FertilizanteCultivo, Date> fechaAplicacion;
    public static volatile SingularAttribute<FertilizanteCultivo, String> anotacion;

}