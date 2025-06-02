package web.modele;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Client;
import metier.modele.Medium;
import metier.service.Service;


public class RequestConsultationAction extends Action {

    public RequestConsultationAction(Service service) {
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
            long idClient = Long.parseLong(authToken);
            long idMedium = Long.parseLong(request.getParameter("idMedium"));
        
            Client client = service.findClientById(idClient);
            Medium medium = service.findMediumById(idMedium);
            
            boolean res = service.demandeConsultation(client, medium);
            
        
            request.setAttribute("result", res);
        }
        else{
            request.setAttribute("result", false);
        }
    }
    
}
