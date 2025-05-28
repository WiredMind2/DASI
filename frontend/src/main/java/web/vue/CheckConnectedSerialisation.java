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
public class CheckConnectedSerialisation extends Serialisation {

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accName = (String) request.getAttribute("accName");

        if (accName != null) {
            response.setContentType("text/plain;charset=UTF-8");
            PrintWriter out = response.getWriter();

            out.println(accName);
            out.close();
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

}
