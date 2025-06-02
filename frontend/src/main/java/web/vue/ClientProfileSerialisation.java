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
import metier.modele.Client;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import metier.modele.ProfilAstral;

/**
 *
 * @author aberton1
 */
public class ClientProfileSerialisation extends Serialisation {

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        Client client = (Client) request.getAttribute("client");
        List<Consultation> consultations = (List<Consultation>) request.getAttribute("consultations");

        ProfilAstral profil_astral = client.getProfil_astral();

        JsonObject profile = new JsonObject();
        profile.addProperty("name", client.getPrenom());
        profile.addProperty("zodiacSign", profil_astral.getSigne_zodiaque() + " ♐"); // les emojis n'existent pas
        profile.addProperty("chineseSign", profil_astral.getSigne_chinois() + " 🐖");
        profile.addProperty("luckyColor", profil_astral.getCouleur() + " 🟡");
        profile.addProperty("totemAnimal", profil_astral.getAnimal() + " 🦊");
        profile.addProperty("quote", "Les étoiles vous guident, mais c'est à vous de choisir votre chemin."); // N'existe pas dans la db?

        JsonArray history = new JsonArray();
        for (Consultation consult : consultations) {
            Medium medium = consult.getMedium();
            JsonObject consultJson = new JsonObject();
            consultJson.addProperty("medium", medium.getDenomination());

            consultJson.addProperty("comment", consult.getCommentaire_employe());
            
            if(consult.getDate_fin() != null){
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                consultJson.addProperty("date", formatter.format(consult.getDate_fin()));
            }else{
                consultJson.addProperty("date", "non finie");
            }

            if (medium instanceof Cartomancien) {
                consultJson.addProperty("type", "Cartomancien");
            } else if (medium instanceof Spirite) {
                consultJson.addProperty("type", "Spirite");
            } else if (medium instanceof Astrologue) {
                consultJson.addProperty("type", "Astrologue");
            }
            
            
            Date debut = consult.getDate_debut();
            Date fin = consult.getDate_fin();
            if(fin != null){
                if(debut == null){
                    debut = new Date(fin.getTime());
                }
                Duration duration = Duration.between(debut.toInstant(), fin.toInstant());
            

                long hours = duration.toHours();
                long minutes = duration.toMinutes() % 60;
                long seconds = duration.getSeconds() % 60;

                StringBuilder durationStr = new StringBuilder();
                if (hours > 0) {
                    durationStr.append(hours).append(" hour");
                    if (hours > 1) {
                        durationStr.append("s");
                    }
                }
                if (minutes > 0) {
                    if (hours > 0) {
                        durationStr.append(" ");
                    }
                    durationStr.append(minutes).append(" min");
                }

                // If duration is exactly 0 minutes
                if (durationStr.length() == 0) {
                    durationStr.append("0 min");
                }
            
                consultJson.addProperty("duration", durationStr.toString());
            }else{
                consultJson.addProperty("duration", "-");
            }
            history.add(consultJson);
        }


        JsonObject result = new JsonObject();
        result.add("profile", profile);
        result.add("history", history);

        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        out.println(gson.toJson(result));
        out.close();

    }

}
