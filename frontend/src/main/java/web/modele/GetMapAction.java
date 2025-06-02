package web.modele;

import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Consultation;
import metier.service.Service;

/**
 *
 * @author aberton1
 */
public class GetMapAction extends Action {

    public GetMapAction(Service service) {
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
            List<Consultation> consultations = service.getHistoriqueConsultationsEmploye(idEmploye);
        
            request.setAttribute("consultations", consultations);
        }
    }
    
}
