/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author ygallard
 */
@Entity
public class Consultation {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date date_debut;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP) //les consultations sans date de fin mais avec date de debut sont en cours
    private Date date_fin;
    private String avis_client;
    private String commentaire_employe;
    @ManyToOne 
    private Client client; 
    @ManyToOne
    private Medium medium;
    @ManyToOne
    private Employe employe;

    //Constructeurs
    public Consultation() {
    }

    public Consultation(Client client, Medium medium, Employe employe) {
        this.client = client;
        this.medium = medium;
        this.employe = employe;
    }
    
    //accesseurs

    public Long getId() {
        return id;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public String getAvis_client() {
        return avis_client;
    }

    public String getCommentaire_employe() {
        return commentaire_employe;
    }

    public Client getClient() {
        return client;
    }

    public Medium getMedium() {
        return medium;
    }

    public Employe getEmploye() {
        return employe;
    }
    
    //setter
    //on ne peut pas changer le client et le medium donc ils sont absents des setters
    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public void setAvis_client(String avis_client) {
        this.avis_client = avis_client;
    }

    public void setCommentaire_employe(String commentaire_employe) {
        this.commentaire_employe = commentaire_employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    //equals et hashcode
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final Consultation other = (Consultation) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
