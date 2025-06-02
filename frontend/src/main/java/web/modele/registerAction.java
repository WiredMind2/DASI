package web.modele;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Client;
import metier.service.Service;

class RegisterData {

    String email;
    String password;
    String nom;
    String prenom;
    String dateNaissance;
    String telephone;
    String addresse;
}

public class registerAction extends Action {

    public registerAction(Service service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
        BufferedReader reader = null;
        try {
            StringBuilder sb = new StringBuilder();
            reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String requestBody = sb.toString();

            System.out.println(requestBody);

            Gson gson = new Gson();
            RegisterData data = gson.fromJson(requestBody, RegisterData.class);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dateNaissance;
            try {
                dateNaissance = sdf.parse(data.dateNaissance);
            } catch (ParseException ex) {
                request.setAttribute("error", "Date de naissance invalide.");
                return;
            }

            Client c = new Client(data.nom, data.prenom, data.email,
                    data.telephone, data.password, data.addresse, dateNaissance);
            boolean res = service.inscriptionClient(c);
            if(res){
                Client client = service.findClientByMail(data.email);
                
                request.setAttribute("error", "ok");
                request.setAttribute("authToken", client.getId());
            } else {
                request.setAttribute("error", "Le serveur a eu un problème.");                
            }

        } catch (IOException ex) {
            Logger.getLogger(registerAction.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(registerAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
