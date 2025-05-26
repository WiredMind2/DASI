/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Temporal;

/**
 *
 * @author ygallard
 */
@Entity
public class Astrologue extends Medium{
    private String formation;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date promotion;

    public Astrologue() {
    }

    public Astrologue(String denomination, String genre, String presentation, int tarif, String formation, Date promotion) {
        super(denomination, genre, presentation, tarif);
        this.formation = formation;
        this.promotion = promotion;
    }

    public String getFormation() {
        return formation;
    }

    public Date getPromotion() {
        return promotion;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public void setPromotion(Date promotion) {
        this.promotion = promotion;
    }

    @Override
    public String toString() {
        return super.toString() + "Astrologue{" + "formation=" + formation + ", promotion=" + promotion + '}';
    }
    
    
    
    
    
}
