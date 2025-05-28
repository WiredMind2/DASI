/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.controleur;

import dao.JpaUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.service.Service;
import web.modele.ajouterCommentaireAction;
import web.modele.checkConnectedAction;
import web.modele.getClientProfileAction;
import web.modele.getConsultationHistoryAction;
import web.modele.getConsultationEnCoursAction;
import web.modele.getInspirationAction;
import web.modele.getMapAction;
import web.modele.getMediumProfileAction;
import web.modele.getMediumsAction;
import web.modele.getStatisticsAction;
import web.modele.loginAction;
import web.modele.registerAction;
import web.modele.requestConsultationAction;
import web.modele.terminerConsultationAction;
import web.vue.BooleanSerialisation;
import web.vue.CheckConnectedSerialisation;
import web.vue.ClientProfileSerialisation;
import web.vue.ConsultationHistorySerialisation;
import web.vue.ConsultationSerialisation;
import web.vue.DisconnectSerialisation;
import web.vue.LoginSerialisation;
import web.vue.MapSerialisation;
import web.vue.MediumProfileSerialisation;
import web.vue.MediumsSerialisation;
import web.vue.RegisterSerialisation;
import web.vue.StatisticsSerialisation;
import web.vue.StringSerialisation;

/**
 *
 * @author aberton1
 */
@WebServlet(name = "ActionServlet", urlPatterns = {"/ActionServlet", "/ActionServlet/*"})
public class ActionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String todo = "";
        if (request.getPathInfo() != null && request.getPathInfo().length() > 0){
            todo = request.getPathInfo().substring(1);
        }
        System.out.println("Path: " + '"' + todo + '"');
        
        Service service = new Service();
        switch(todo){
            case "getMediums" : {
                new getMediumsAction(service).execute(request);
                new MediumsSerialisation().appliquer(request, response);
                break;
            }
            case "medium" : {
                new getMediumProfileAction(service).execute(request);
                new MediumProfileSerialisation().appliquer(request,response);
                break;
            }
            case "getConsultationHistory" : {
                new getConsultationHistoryAction(service).execute(request);
                new ConsultationHistorySerialisation().appliquer(request,response);
                break;
            }
            case "getStatistics" : {
                new getStatisticsAction(service).execute(request);
                new StatisticsSerialisation().appliquer(request,response);
                break;
            }
            case "getInspiration" : {
                new getInspirationAction(service).execute(request);
                new StringSerialisation().appliquer(request, response);
            }
            case "getConsultation" : {
                new getConsultationEnCoursAction(service).execute(request);
                new ConsultationSerialisation().appliquer(request, response);
                break;
            }
            case "getMap" : {
                new getMapAction(service).execute(request);
                new MapSerialisation().appliquer(request, response);
                break;
            }
            case "requestConsultation" : {
                new requestConsultationAction(service).execute(request);
                new BooleanSerialisation().appliquer(request,response);
                break;
            }
            case "terminerConsultation" : {
                new terminerConsultationAction(service).execute(request);
                new BooleanSerialisation().appliquer(request, response);
                break;
            }
            case "ajouterCommentaire" : {
                new ajouterCommentaireAction(service).execute(request);
                new BooleanSerialisation().appliquer(request, response);
            }
            case "login" : {
                new loginAction(service).execute(request);
                new LoginSerialisation().appliquer(request, response);
                break;
            }
            case "register" : {
                new registerAction(service).execute(request);
                new RegisterSerialisation().appliquer(request, response);
                break;
            }
            case "getClient" : {
                new getClientProfileAction(service).execute(request);
                new ClientProfileSerialisation().appliquer(request, response);
                break;
            }
            case "checkConnected" : {
                new checkConnectedAction(service).execute(request);
                new CheckConnectedSerialisation().appliquer(request, response);
                break;
            }
            case "disconnect" : {
                new DisconnectSerialisation().appliquer(request, response);
                break;
            }
            default : {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    public void init(){
        JpaUtil.creerFabriquePersistance();
    }
    
    @Override
    public void destroy(){
        JpaUtil.fermerContextePersistance();
    }

}
