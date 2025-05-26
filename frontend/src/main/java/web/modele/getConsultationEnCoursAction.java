/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.modele;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Client;
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
            long idClient = Long.parseLong(request.getParameter("idClient"));
            long idMedium = Long.parseLong(authToken);
        
            Client client = service.findClientById(idClient);
            Medium medium = service.findMediumById(idMedium);
        
            boolean res = service.demandeConsultation(client, medium);
        
            request.setAttribute("resultConsultation", res);
        }
        else{
        }
    }
    
}
