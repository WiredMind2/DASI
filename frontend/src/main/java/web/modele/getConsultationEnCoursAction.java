/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.modele;

import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Client;
import metier.modele.Consultation;
import metier.modele.Employe;
import metier.modele.Medium;
import metier.service.Service;

/**
 *
 * @author aberton1
 */
public class getConsultationEnCoursAction extends Action {

    public getConsultationEnCoursAction(Service service) {
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
            long idEmploye = Long.parseLong(authToken);
            Employe employe = service.findEmployeById(idEmploye);

            Consultation consultation = service.findConsultationEnCours(employe);
            List<Consultation> histConsuls = service.getHistoriqueConsultationsClient(consultation.getClient().getId());

            request.setAttribute("consultation", consultation);
            request.setAttribute("histConsuls", histConsuls);
        }
        else{
        }
    }
    
}
