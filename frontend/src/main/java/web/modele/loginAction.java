package web.modele;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Client;
import metier.modele.Employe;
import metier.service.Service;

class LoginData {

    String email;
    String password;
}

public class loginAction extends Action {

    public loginAction(Service service) {
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

            Gson gson = new Gson();
            LoginData data = gson.fromJson(requestBody, LoginData.class);

            String email = data.email;
            String password = data.password;
            System.out.println("Email: " + email + " - password: " + password);

            Object utilisateur = service.authentifierUtilisateur(email, password);
            if (utilisateur == null) {
                System.out.println("Aucun utilisateur trouvé avec ces identifiants.");
                request.setAttribute("error", "Identifiants invalide");
            } else if (utilisateur instanceof Client) {
                Client client = (Client) utilisateur;
                System.out.println("Client authentifié : " + client.getPrenom() + " " + client.getNom());
                request.setAttribute("authToken", client.getId());
                request.setAttribute("accType", "client");
            } else if (utilisateur instanceof Employe) {
                Employe employe = (Employe) utilisateur;
                System.out.println("Employé authentifié : " + employe.getPrenom() + " " + employe.getNom());
                request.setAttribute("authToken", employe.getId());
                request.setAttribute("accType", "employe");
            }

        } catch (IOException ex) {
            Logger.getLogger(loginAction.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(loginAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
