/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import com.google.maps.model.LatLng;

/**
 *
 * @author ygallard
 */
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String nom;
    private String prenom;
    @Column(unique = true)
    private String mail;
    private String tel;
    private String mdp;
    private String adresse;
    private LatLng coord_client;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date_naissance;
    @Embedded
    private ProfilAstral profil_astral;
    
    
    
    
    //constructeurs
    public Client() {
    }
    
    public Client(String nom, String prenom, String mail, String tel, String mdp, String adresse, Date date_naissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.tel = tel;
        this.mdp = mdp;
        this.adresse = adresse;
        this.date_naissance = date_naissance;
    }
    
    //accesseurs

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMail() {
        return mail;
    }

    public String getTel() {
        return tel;
    }

    public String getMdp() {
        return mdp;
    }

    public String getAdresse() {
        return adresse;
    }

    public LatLng getCoord_client() {
        return coord_client;
    }
    
    public Date getDate_naissance() {
        return date_naissance;
    }
    
    public ProfilAstral getProfil_astral(){
        return profil_astral;
    }
    
    
    
    
    //setters

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setCoord_client(LatLng coord_client) {
        this.coord_client = coord_client;
    }

    public void setDate_naissance(Date date_naissance) {
        this.date_naissance = date_naissance;
    }
    
    public void setProfil_astral(ProfilAstral profil_astral) {
        this.profil_astral = profil_astral;
    }

    
    
    
    
    //redefinition des méthodes toString, equals etc

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", mail=" + mail + ", tel=" + tel + ", mdp=" + mdp + ", adresse=" + adresse + ", coordonnées GPS=" + coord_client + ", date_naissance=" + date_naissance + ", " + profil_astral.toString() + "}";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Client other = (Client) obj;
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        
        return true;
    }
    
    
    
    
   
    
    
}
