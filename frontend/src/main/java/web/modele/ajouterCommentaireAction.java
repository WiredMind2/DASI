package web.modele;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Consultation;
import metier.modele.Employe;
import metier.service.Service;


public class ajouterCommentaireAction extends Action {

    public ajouterCommentaireAction(Service service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
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

        if (authToken != null) {
            try {
                long idEmploye = Long.parseLong(authToken);
                Employe employe = service.findEmployeById(idEmploye);
                Consultation consultation = service.findConsultationEnCours(employe);
                
                BufferedReader reader = null;
                StringBuilder sb = new StringBuilder();
                reader = request.getReader();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                String commentaire = sb.toString();
                
                System.out.println("Comm: " + commentaire);
                boolean res = service.ajouterCommentaireEmploye(consultation, commentaire);
                if (res == true) {
                    res = service.terminerConsultation(consultation);
                }
                System.out.println("Comm2: " + res);
                request.setAttribute("result", res);
                return;
            } catch (IOException ex) {
                Logger.getLogger(AjouterCommentaireAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        request.setAttribute("result", false);
    }

}
