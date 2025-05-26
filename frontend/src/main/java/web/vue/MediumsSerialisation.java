/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.vue;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Astrologue;
import metier.modele.Cartomancien;
import metier.modele.Medium;
import metier.modele.Spirite;

/**
 *
 * @author aberton1
 */
public class MediumsSerialisation extends Serialisation{

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out  =response.getWriter();
        
        List<Medium> mediums = (List<Medium>)request.getAttribute("mediums");
        JsonArray cartomenciens = new JsonArray();
        JsonArray spirites = new JsonArray();
        JsonArray astrologues = new JsonArray();
        
        for (Medium med : mediums){
            JsonObject jsonMedium = new JsonObject();
            jsonMedium.addProperty("name", med.getDenomination());
            jsonMedium.addProperty("description", med.getPresentation());
            jsonMedium.addProperty("link", "medium.html?id="+med.getId());
            if(med instanceof Cartomancien){
                cartomenciens.add(jsonMedium);
            }
            if(med instanceof Spirite){
                spirites.add(jsonMedium);
            }
            if(med instanceof Astrologue){
                astrologues.add(jsonMedium);
            }
        }
        
        JsonArray result = new JsonArray();
        JsonObject container = new JsonObject();
        container.addProperty("category", "Cartomancien");
        container.add("professionals", cartomenciens);
        result.add(container);
        
        container = new JsonObject();
        container.addProperty("category", "Spirite");
        container.add("professionals", spirites);
        result.add(container);
        
        container = new JsonObject();
        container.addProperty("category", "Astrologue");
        container.add("professionals", astrologues);
        result.add(container);
        
        Gson gson = new Gson();
        out.println(gson.toJson(result));
        
        
        out.close();
    }
    
}
