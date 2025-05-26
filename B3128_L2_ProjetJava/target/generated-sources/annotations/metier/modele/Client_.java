package metier.modele;

import com.google.maps.model.LatLng;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import metier.modele.ProfilAstral;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-05-26T21:11:59")
@StaticMetamodel(Client.class)
public class Client_ { 

    public static volatile SingularAttribute<Client, ProfilAstral> profil_astral;
    public static volatile SingularAttribute<Client, LatLng> coord_client;
    public static volatile SingularAttribute<Client, String> mail;
    public static volatile SingularAttribute<Client, String> mdp;
    public static volatile SingularAttribute<Client, String> adresse;
    public static volatile SingularAttribute<Client, String> tel;
    public static volatile SingularAttribute<Client, Long> id;
    public static volatile SingularAttribute<Client, String> nom;
    public static volatile SingularAttribute<Client, String> prenom;
    public static volatile SingularAttribute<Client, Date> date_naissance;

}