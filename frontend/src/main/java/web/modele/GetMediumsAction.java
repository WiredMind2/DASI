package web.modele;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Medium;
import metier.service.Service;


public class GetMediumsAction extends Action {

    public GetMediumsAction(Service service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
        
        List<Medium> mediums = service.listerMediums();
        request.setAttribute("mediums", mediums);
    }
    
}
