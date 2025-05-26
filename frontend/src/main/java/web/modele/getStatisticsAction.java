/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.modele;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import metier.service.Service;

/**
 *
 * @author tlafondela
 */
public class getStatisticsAction extends Action {

    public getStatisticsAction(Service service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
         List<Object[]> top5 = service.getTop5Mediums();
         request.setAttribute("top5", top5);
    }
    
}
