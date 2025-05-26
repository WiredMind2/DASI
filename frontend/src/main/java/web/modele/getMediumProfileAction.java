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
public class getMediumProfileAction extends Action {

    public getMediumProfileAction(Service service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter("id"));
        Medium medium = service.findMediumById(id);
        request.setAttribute("medium", medium);
        List<Consultation> consultations = service.getHistoriqueConsultationsMedium(id);
        request.setAttribute("consultations", consultations);
    }
    
}
