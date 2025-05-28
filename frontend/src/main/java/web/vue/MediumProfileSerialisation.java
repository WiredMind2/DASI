/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.vue;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Medium;
import metier.modele.Cartomancien;
import metier.modele.Spirite;
import metier.modele.Astrologue;
import metier.modele.Consultation;
import java.text.SimpleDateFormat;

/**
 *
 * @author aberton1
 */
public class MediumProfileSerialisation extends Serialisation{

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        
        Medium medium = (Medium)request.getAttribute("medium");
        List<Consultation> consultations = (List<Consultation>)request.getAttribute("consultations");
        

        JsonObject result = new JsonObject();
        JsonObject profile = new JsonObject();
        profile.addProperty("name", medium.getDenomination());

        if(medium instanceof Cartomancien){
            profile.addProperty("type","Cartomencien");
        }
        else if(medium instanceof Spirite){
            profile.addProperty("type","Spirite");
        }
        else if(medium instanceof Astrologue){
            profile.addProperty("type","Astrologue");
        }
        profile.addProperty("presentation", medium.getPresentation());
        
        JsonArray testimonials = new JsonArray();
        for(Consultation consult : consultations){
            if (consult.getDate_fin() != null){
                JsonObject consultJson = new JsonObject();
                consultJson.addProperty("name", consult.getClient().getPrenom() + " " + consult.getClient().getNom().charAt(0) + ".");
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                consultJson.addProperty("date", formatter.format(consult.getDate_fin()));
                consultJson.addProperty("test", consult.getAvis_client());
                testimonials.add(consultJson);
            }
        }

        //Les attributs suivants sont des valeurs par défaut car non fourni par le backend pour la réalisation du front
        profile.addProperty("image", "crystal-ball.webp");
        profile.addProperty("rating", 0);
        JsonArray specialities = new JsonArray();
        specialities.add("Communication avec l'au-delà");
        specialities.add("GuidanceSpirituelle");
        specialities.add("Mediumnité par écrit");
        specialities.add("Interprétation des signes");
        profile.add("specialties", specialities);

        result.add("profile",profile);
        result.add("testimonials",testimonials);

        PrintWriter out  =response.getWriter();
        Gson gson = new Gson();
        out.println(gson.toJson(result));
        out.close();
        
    }
    
}
