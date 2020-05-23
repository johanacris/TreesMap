package Modelo;

import Modelo.FertilizanteCultivo;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-05-03T16:36:05")
@StaticMetamodel(Fertilizante.class)
public class Fertilizante_ { 

    public static volatile SingularAttribute<Fertilizante, String> nombreFertilizante;
    public static volatile ListAttribute<Fertilizante, FertilizanteCultivo> fertilizanteCultivoList;
    public static volatile SingularAttribute<Fertilizante, Integer> idFertilizante;
    public static volatile SingularAttribute<Fertilizante, String> tipoFertilizante;

}