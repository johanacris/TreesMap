package Modelo;

import Modelo.Cultivo;
import Modelo.Veneno;
import Modelo.VenenoCultivoPK;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-03T16:36:05")
@StaticMetamodel(VenenoCultivo.class)
public class VenenoCultivo_ { 

    public static volatile SingularAttribute<VenenoCultivo, Cultivo> cultivo;
    public static volatile SingularAttribute<VenenoCultivo, VenenoCultivoPK> venenoCultivoPK;
    public static volatile SingularAttribute<VenenoCultivo, Date> fechaAplicacion;
    public static volatile SingularAttribute<VenenoCultivo, Veneno> veneno;
    public static volatile SingularAttribute<VenenoCultivo, String> anotacion;

}