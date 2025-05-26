package web.vue;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Medium;

/**
 *
 * @author aberton1
 */
public class StatisticsSerialisation extends Serialisation{

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        List<Object[]> top5 =  (List<Object[]>)request.getAttribute("top5");
        
        JsonObject result = new JsonObject();
        JsonArray topMediumsJson = new JsonArray();
         for(Object[] top : top5){
             Medium medium = (Medium)top[0];
             long nbConsult = (long)top[1];
             
             JsonObject topJson = new JsonObject();
             topJson.addProperty("denomination", medium.getDenomination());
             topJson.addProperty("consultations", nbConsult);
             topMediumsJson.add(topJson);
         }
         
         result.add("topMediums", topMediumsJson);
         
         //Valeur en dure pas de service
         result.addProperty("consultationsLast7Days", -1);
         
         String leaderboardJsonString = "["
        + "{\"prenom\":\"Alice\", \"nom\":\"Dupont\", \"nbClients\":12},"
        + "{\"prenom\":\"Bob\", \"nom\":\"Martin\", \"nbClients\":10},"
        + "{\"prenom\":\"Claire\", \"nom\":\"Durand\", \"nbClients\":8},"
        + "{\"prenom\":\"David\", \"nom\":\"Bernard\", \"nbClients\":7}"
        + "]";

        // Parser la chaîne en JsonArray
        JsonParser parser = new JsonParser();
        JsonArray leaderboard = parser.parse(leaderboardJsonString).getAsJsonArray();

        // Ajouter au résultat
        result.add("leaderboard", leaderboard);
        
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        out.println(gson.toJson(result));
        out.close();
    }
}


