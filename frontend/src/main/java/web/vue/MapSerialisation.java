
package web.vue;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.maps.model.LatLng;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Consultation;

/**
 *
 * @author aberton1
 */
public class MapSerialisation extends Serialisation{

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        
        
        List<Consultation> consultations = (List<Consultation>)request.getAttribute("consultations");
        JsonArray result = new JsonArray();
        
        for(Consultation consult : consultations)
        {
            JsonObject consultationJson = new JsonObject();
            consultationJson.addProperty("client", consult.getClient().getPrenom());
            LatLng coord = consult.getClient().getCoord_client();
            if(coord != null){
                consultationJson.addProperty("lat", coord.lat);
                consultationJson.addProperty("lng", coord.lng);
            }else{
                consultationJson.addProperty("lat", "null");
                consultationJson.addProperty("lng", "null");
            }
            result.add(consultationJson);
        }
        
        PrintWriter out  =response.getWriter();
        Gson gson = new Gson();
        out.println(gson.toJson(result));
        
        
        out.close();
    }
    
}