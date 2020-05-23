package Modelo;

import Modelo.FertilizanteCultivo;
import Modelo.PlagaCultivo;
import Modelo.Usuario;
import Modelo.VenenoCultivo;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-03T16:36:05")
@StaticMetamodel(Cultivo.class)
public class Cultivo_ { 

    public static volatile SingularAttribute<Cultivo, String> latitud;
    public static volatile ListAttribute<Cultivo, FertilizanteCultivo> fertilizanteCultivoList;
    public static volatile ListAttribute<Cultivo, PlagaCultivo> plagaCultivoList;
    public static volatile SingularAttribute<Cultivo, String> nombreCultivo;
    public static volatile SingularAttribute<Cultivo, Date> fechaSembtado;
    public static volatile ListAttribute<Cultivo, VenenoCultivo> venenoCultivoList;
    public static volatile SingularAttribute<Cultivo, String> clase;
    public static volatile SingularAttribute<Cultivo, String> especie;
    public static volatile SingularAttribute<Cultivo, String> division;
    public static volatile SingularAttribute<Cultivo, String> longitud;
    public static volatile SingularAttribute<Cultivo, String> genero;
    public static volatile SingularAttribute<Cultivo, Integer> idCultivo;
    public static volatile SingularAttribute<Cultivo, Usuario> usuario;
    public static volatile SingularAttribute<Cultivo, String> orden;
    public static volatile SingularAttribute<Cultivo, String> familia;

}