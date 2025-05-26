/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author ygallard
 */
@Entity
public class Employe {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String nom;
    private String prenom;
    private String genre;
    @Column(unique = true)
    private String mail;
    private String tel;
    private String mdp;
    private Boolean disponible = true; 
    
    
    //constructeurs
    public Employe() {
    }

    public Employe(String nom, String prenom, String genre, String mail, String tel, String mdp) {
        this.nom = nom;
        this.prenom = prenom;
        this.genre = genre;
        this.mail = mail;
        this.tel = tel;
        this.mdp = mdp;
        
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

    public String getGenre() {
        return genre;
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

    public Boolean getDisponible() {
        return disponible;
    }

   
    
    
    //setters

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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


    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }
    
    
    //redéfnition des méthodes toString, equals etc

    @Override
    public String toString() {
        return "Employe{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", genre=" + genre + ", mail=" + mail + ", tel=" + tel + ", mdp=" + mdp + ", disponible=" + disponible + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.id);
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
        final Employe other = (Employe) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
