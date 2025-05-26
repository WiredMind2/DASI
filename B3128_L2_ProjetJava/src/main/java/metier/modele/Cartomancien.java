/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import javax.persistence.Entity;


/**
 *
 * @author ygallard
 */
@Entity
public class Cartomancien extends Medium {

    public Cartomancien() {
    }
    
    public Cartomancien(String denomination, String genre, String presentation, int tarif) {
        super(denomination, genre, presentation, tarif);
        
    }
    
    
    
}
