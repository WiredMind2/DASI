/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.modele;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Client;
import metier.service.Service;

public class TestAction extends Action {

    public TestAction(Service service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {

        List<Client> clients = service.listerClients();
        request.setAttribute("clients", clients);
    }

}
