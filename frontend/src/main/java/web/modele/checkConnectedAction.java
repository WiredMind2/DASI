/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.modele;

import com.google.gson.Gson;
import static console.Main.printlnConsoleIHM;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Client;
import metier.modele.Employe;
import metier.modele.Medium;
import metier.service.Service;

public class checkConnectedAction extends Action {

    public checkConnectedAction(Service service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        String authToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("authToken".equals(cookie.getName())) {
                    authToken = cookie.getValue();
                    break;
                }
            }
        }

        long id = Long.parseLong(authToken);
        Client client = service.findClientById(id);
        if (client != null) {
            request.setAttribute("accName", client.getPrenom());
            return;
        }
        Employe employe = service.findEmployeById(id);
        if (employe != null) {
            request.setAttribute("accName", employe.getPrenom());
            return;            
        }
    }

}
