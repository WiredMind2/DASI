/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.modele;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Client;
import metier.modele.Consultation;
import metier.modele.Employe;
import metier.service.Service;

class PayloadData {

    String amour;
    String travail;
    String sante;
}

public class getInspirationAction extends Action {

    public getInspirationAction(Service service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
        PayloadData data;
        try {
            BufferedReader reader = null;
            StringBuilder sb = new StringBuilder();
            reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String requestBody = sb.toString();
            Gson gson = new Gson();
            data = gson.fromJson(requestBody, PayloadData.class);
        } catch (IOException ex) {
            Logger.getLogger(getInspirationAction.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        Cookie[] cookies = request.getCookies();
        String authToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("authToken".equals(cookie.getName())) {
                    authToken = cookie.getValue();
                    break;
                }
            }
        }

        if (authToken == null) {
            return;
        }
        long idEmploye = Long.parseLong(authToken);
        Employe employe = service.findEmployeById(idEmploye);
        Consultation consultation = service.findConsultationEnCours(employe);
        if (consultation == null) {
            return;
        }
        Client client = consultation.getClient();

        int amour = Integer.parseInt(data.amour);
        int travail = Integer.parseInt(data.travail);
        int sante = Integer.parseInt(data.sante);

        try {
            List<String> inspi = service.demanderAideInspiration(client, amour, sante, travail);
            
            request.setAttribute("text", String.join("</br></br>", inspi));
        } catch (IOException ex) {
            Logger.getLogger(getInspirationAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
