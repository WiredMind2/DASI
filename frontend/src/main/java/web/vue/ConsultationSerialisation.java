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
        Consultation consultation = (Consultation) request.getAttribute("consultation");
        if (consultation == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        List<Consultation> histConsuls = (List<Consultation>) request.getAttribute("histConsuls");

        response.setContentType("application/json;charset=UTF-8");

        Client client = consultation.getClient();
        ProfilAstral profilAstral = client.getProfil_astral();

        JsonObject consultationData = new JsonObject();
        consultationData.addProperty("medium", consultation.getMedium().getDenomination());

        JsonObject clientData = new JsonObject();
        clientData.addProperty("firstName", client.getPrenom());
        clientData.addProperty("lastName", client.getNom());
        consultationData.add("client", clientData);

        JsonArray history = new JsonArray();
        for (Consultation consult : histConsuls) {
            JsonObject consultData = new JsonObject();

            Date dateConsul = consult.getDate_fin();
            if (dateConsul == null) {
                continue;
            }
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            consultData.addProperty("date", formatter.format(dateConsul));

            String commentaire = consult.getCommentaire_employe();
            if (commentaire == null) {
                commentaire = "Pas de commentaire";
            }
            consultData.addProperty("comment", commentaire);

            history.add(consultData);
        }

        consultationData.add("previousConsultations", history);

        JsonObject clientProfile = new JsonObject();
        clientProfile.addProperty("Couleur", profilAstral.getCouleur());
        clientProfile.addProperty("Signe chinois", profilAstral.getSigne_chinois());
        clientProfile.addProperty("Animal totem", profilAstral.getAnimal());
        clientProfile.addProperty("Signe zodiaque", profilAstral.getSigne_zodiaque());

        consultationData.add("clientProfile", clientProfile);

        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        out.println(gson.toJson(consultationData));
        out.close();

    }

}
