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
public class ConsultationSerialisation extends Serialisation {

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        Consultation consultation = (Consultation) request.getAttribute("consultation");
        List<Consultation> histConsuls = (List<Consultation>) request.getAttribute("histConsuls");

        JsonObject consultationData = new JsonObject();
        consultationData.addProperty("medium", consultation.getMedium().getDenomination());
        
        JsonObject clientData = new JsonObject();
        clientData.addProperty("firstName", consultation.getClient().getPrenom());
        clientData.addProperty("lastName", consultation.getClient().getNom());
        consultationData.add("client", clientData);
        
        
        
        JsonArray history = new JsonArray();
        for (Consultation consult : histConsuls) {
            JsonObject consultData = new JsonObject();

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            consultData.addProperty("date", formatter.format(consult.getDate_fin()));

            consultData.addProperty("comment", consult.getCommentaire_employe());

            history.add(consultData);
        }


        JsonObject result = new JsonObject();
        //result.add("profile", profile);
        result.add("history", history);

        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        out.println(gson.toJson(result));
        out.close();

    }

}
