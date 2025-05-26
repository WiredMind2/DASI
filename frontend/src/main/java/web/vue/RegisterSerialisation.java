/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.vue;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aberton1
 */
public class RegisterSerialisation extends Serialisation{

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        
        String error = (String)request.getAttribute("error");
        
        Long authToken = (Long)request.getAttribute("authToken");
        
        if(authToken != null){
            Cookie cookie = new Cookie("authToken", authToken.toString());
            cookie.setMaxAge(60 * 60); // expires in 1 hour
            response.addCookie(cookie);
        }
        
        JsonObject result = new JsonObject();
        result.addProperty("error", error);
        //result.addProperty("authToken", authToken);
        
        Gson gson = new Gson();
        out.println(gson.toJson(result));
        
        out.close();
    }
    
}
