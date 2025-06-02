package web.modele;

import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Client;
import metier.service.Service;
import metier.modele.Consultation;

/**
 *
 * @author tlafondela
 */
public class GetClientProfileAction extends Action {

    public GetClientProfileAction(Service service) {
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
