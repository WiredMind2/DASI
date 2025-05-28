
package web.vue;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Consultation;

/**
 *
 * @author aberton1
 */
public class ConsultationHistorySerialisation extends Serialisation{

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        
        
        List<Consultation> consultations = (List<Consultation>)request.getAttribute("consultations");
        JsonArray result = new JsonArray();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        for(Consultation consult : consultations)
        {
            JsonObject consultationJson = new JsonObject();
            if(consult.getDate_fin()!= null){
                consultationJson.addProperty("date", formatter.format(consult.getDate_fin()));
            }else{
                consultationJson.addProperty("date","non finie");
            }
            consultationJson.addProperty("client", consult.getClient().getPrenom()+" "+consult.getClient().getNom());
            consultationJson.addProperty("medium", consult.getMedium().getDenomination());
            consultationJson.addProperty("avisClient", "null");

            if(consult.getCommentaire_employe() != null){
                consultationJson.addProperty("commentEmploye", consult.getCommentaire_employe());
            }else{
                consultationJson.addProperty("commentEmploye", "null");
            }
            result.add(consultationJson);
        }
        
        PrintWriter out  =response.getWriter();
        Gson gson = new Gson();
        out.println(gson.toJson(result));
        
        
        out.close();
    }
    
}
