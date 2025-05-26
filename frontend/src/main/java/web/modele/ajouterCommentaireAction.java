/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.modele;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Client;
import metier.modele.Consultation;
import metier.modele.Medium;
import metier.service.Service;

/**
 *
 * @author aberton1
 */
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
        
        if(authToken != null){
            long idConsultation = Long.parseLong(request.getParameter("idConsultation"));
            String commentaire = request.getParameter("idConsultation");

            Consultation consultation = service.findConsultationById(idConsultation);
            boolean res = service.ajouterCommentaireEmploye(consultation, commentaire);
        
            request.setAttribute("result", res);
        }
        else{
            request.setAttribute("result", false);
        }
    }
    
}
