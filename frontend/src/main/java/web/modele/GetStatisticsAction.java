package web.modele;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import metier.service.Service;


public class GetStatisticsAction extends Action {

    public GetStatisticsAction(Service service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
         List<Object[]> top5 = service.getTop5Mediums();
         request.setAttribute("top5", top5);
    }
    
}
