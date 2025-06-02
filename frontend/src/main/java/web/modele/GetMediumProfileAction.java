
package web.modele;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Medium;
import metier.service.Service;
import metier.modele.Consultation;


public class GetMediumProfileAction extends Action {

    public GetMediumProfileAction(Service service) {
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
