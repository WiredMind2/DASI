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
import metier.modele.Medium;
import metier.service.Service;
import metier.modele.Consultation;

/**
 *
 * @author tlafondela
 */
public class getClientProfileAction extends Action {

    public getClientProfileAction(Service service) {
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
        if (authToken == null) {return;}
        long id = Long.parseLong(authToken);
        Client client = service.findClientById(id);
        
        List<Consultation> consultations = service.getHistoriqueConsultationsClient(client.getId());
        
        request.setAttribute("client", client);
        request.setAttribute("consultations", consultations);
    }

}
