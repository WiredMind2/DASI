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
import javax.servlet.http.Cookie;
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
public class LoginSerialisation extends Serialisation{

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        
        String error = (String)request.getAttribute("error");
        
        
        
        Long authToken = (Long)request.getAttribute("authToken");
        String accType = (String)request.getAttribute("accType");
        
        if(authToken != null){
            Cookie cookie = new Cookie("authToken", authToken.toString());
            cookie.setMaxAge(60 * 60); // expires in 1 hour
            response.addCookie(cookie);
        }
        
        JsonObject result = new JsonObject();
        result.addProperty("error", error);
        //result.addProperty("authToken", authToken);
        result.addProperty("accType", accType);
        
        Gson gson = new Gson();
        out.println(gson.toJson(result));
        
        out.close();
    }
    
}
