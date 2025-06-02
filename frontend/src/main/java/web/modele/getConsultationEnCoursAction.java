package web.modele;

import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Client;
import metier.modele.Consultation;
import metier.modele.Employe;
import metier.service.Service;


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
            if(consultation == null){
                return;
            }
            Client client = consultation.getClient();

            List<Consultation> histConsuls = service.getHistoriqueConsultationsClient(client.getId());

            request.setAttribute("consultation", consultation);
            request.setAttribute("histConsuls", histConsuls);
        }
    }
    
}
