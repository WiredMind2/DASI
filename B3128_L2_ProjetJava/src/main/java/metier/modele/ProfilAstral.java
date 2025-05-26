/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

/**
 *
 * @author ygallard
 */

@Embeddable
public class ProfilAstral {
    private String signe_zodiaque;
    private String signe_chinois;
    private String couleur;
    private String animal;
    
    //constructeurs

    public ProfilAstral(String signe_zodiaque, String signe_chinois, String couleur, String animal) {
        this.signe_zodiaque = signe_zodiaque;
        this.signe_chinois = signe_chinois;
        this.couleur = couleur;
        this.animal = animal;
    }

    public ProfilAstral() {
    }
    
    //setters
    public void setSigne_zodiaque(String signe_zodiaque) {
        this.signe_zodiaque = signe_zodiaque;
    }

    public void setSigne_chinois(String signe_chinois) {
        this.signe_chinois = signe_chinois;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }
    
    //getters
    public String getSigne_zodiaque() {
        return signe_zodiaque;
    }

    public String getSigne_chinois() {
        return signe_chinois;
    }

    public String getCouleur() {
        return couleur;
    }

    public String getAnimal() {
        return animal;
    }
    
    //to string
    @Override
    public String toString() {
        return "ProfilAstral{" + "signe_zodiaque=" + signe_zodiaque + ", signe_chinois=" + signe_chinois + ", couleur=" + couleur + ", animal=" + animal + '}';
    }
    
    
    
}
