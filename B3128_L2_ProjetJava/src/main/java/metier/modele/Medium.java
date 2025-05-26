/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.util.List;
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
public class Medium {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String denomination;
    private String genre;
    private String presentation;
    private int tarif;
    
    // Constructeur
    public Medium(){
    }

    public Medium(String denomination, String genre, String presentation, int tarif) {
        this.denomination = denomination;
        this.genre = genre;
        this.presentation = presentation;
        this.tarif = tarif;
    }

    //setters
    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public void setTarif(int tarif) {
        this.tarif = tarif;
    }

    
    
    
    //getters
    public Long getId() {
        return id;
    }

    

    public String getDenomination() {
        return denomination;
    }

    public String getGenre() {
        return genre;
    }

    public String getPresentation() {
        return presentation;
    }

    public int getTarif() {
        return tarif;
    }

    @Override
    public String toString() {
        return "Medium{" + "id=" + id + ", denomination=" + denomination + ", genre=" + genre + ", presentation=" + presentation + ", tarif=" + tarif + '}';
    }
    
    
}
