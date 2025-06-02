package web.modele;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Client;
import metier.modele.Employe;
import metier.service.Service;

public class checkConnectedAction extends Action {

    public checkConnectedAction(Service service) {
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
        
        if (authToken == null) {return;} //TODO J'ai mis le if pour éviter l'éeereur mais côté front il faudrait qu'on ajoute de quoi gérer ça car là juste connecté avec aucun compte

        long id = Long.parseLong(authToken);
        Client client = service.findClientById(id);
        if (client != null) {
            request.setAttribute("accName", client.getPrenom());
            return;
        }
        Employe employe = service.findEmployeById(id);
        if (employe != null) {
            request.setAttribute("accName", employe.getPrenom());
            return;            
        }
    }

}
