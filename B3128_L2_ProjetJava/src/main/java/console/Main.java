/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package console;

import dao.JpaUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import metier.modele.Client;
import metier.modele.Astrologue;
import metier.modele.Cartomancien;
import metier.modele.Spirite;
import metier.modele.Medium;
import metier.modele.Employe;
import metier.modele.Consultation;
import metier.service.Service;
import dao.*;


/**
 *
 * @author ygallard
 */
public class Main {
    public static void main(String[] args) throws ParseException {
        JpaUtil.desactiverLog();
        JpaUtil.creerFabriquePersistance();
        testerInscriptionClients();
        testListerClients();
        testAjoutMedium();
        testListerMediums();
        testAjoutEmploye();
        testListerEmployes();
        testAuthentifierUtilisateur();
        //testDemandeConsultations();
        testDemanderAideInspiration();
        testTerminerConsultations();
        testAjouterCommentaireEmploye();
        testAjouterAvisClient();
        testListerConsultations();
        testVoirConsultationsPassees();
        testStats();
        JpaUtil.fermerFabriquePersistance();
    }
    
    private static void testerInscriptionClients() throws ParseException {
        System.out.print("--------------TEST D'INSCRIPTION DE CLIENTS (le dernier doit rater)--------------");
        Service service = new Service();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dateNaissance = sdf.parse("01/01/1990");

        for (int i = 1; i <= 4; i++) {
            Client c = new Client("NomClient" + i, "PrenomClient" + i, "client" + i + "@mail.com",
                    "060000000" + i, "mdp" + i, "10 rue ClientVille " + i, dateNaissance);
            boolean res = service.inscriptionClient(c);
            printlnConsoleIHM("Inscription client " + i + " : " + res);
        }
        Client c = new Client("NomClientFAUX", "PrenomClientFAUX" + 5, "client" + 4 + "@mail.com",
                    "060000000" + 4, "mdp" + 4, "10 rue du Totem VILLEURBANNE", dateNaissance);
            boolean res = service.inscriptionClient(c);
            printlnConsoleIHM("Inscription client RATE: " + res);
    }
    
    private static void testListerClients() throws ParseException {
        System.out.print("--------------TEST DE LISTAGE DES CLIENTS--------------");
        printlnConsoleIHM("Liste des clients");
        Service service = new Service();
        List<Client> clients = service.listerClients();
        int nb_client=0;
        for (Client c : clients){
            nb_client++;
            printlnConsoleIHM("client "+nb_client+": "+ c.toString());
        }
    }
    private static void testAjoutMedium() throws ParseException {
        System.out.print("--------------TEST D'AJOUT DE MEDIUM DANS LA DB--------------");
        Service service = new Service();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date promo = sdf.parse("2010");

        service.ajoutMedium(new Medium("Medium1", "F", "Présentation 1", 100));
        service.ajoutMedium(new Cartomancien("Cartomancien1", "H", "Lecture des cartes", 200));
        service.ajoutMedium(new Spirite("Spirite1", "F", "Parle aux esprits", 1500, "Médium confirmé"));
        service.ajoutMedium(new Astrologue("Astrologue1","H", "Voir dans les astres", 800, "INSASTRAL", promo));
        service.ajoutMedium(new Medium("Medium5", "F", "Présentation 5", 50));
    }
    private static void testListerMediums() throws ParseException {
        System.out.print("--------------TEST DE LISTAGE DES MEDIUMS--------------\n");
        printlnConsoleIHM("Liste des employes");
        Service service = new Service();
        List<Medium> mediums = service.listerMediums();
        int nb_medium=0;
        for (Medium m : mediums){
            nb_medium++;
            printlnConsoleIHM("client "+nb_medium+": "+ m.toString());
        }
    }
    private static void testAjoutEmploye() {
        System.out.print("--------------TEST D'AJOUT D'EMPLOYE DANS LA DB (le dernier doit rater)--------------\n");
        Service service = new Service();
        for (int i = 1; i <= 4; i++) {
            Employe e = new Employe("NomEmploye" + i, "PrenomEmploye" + i,
                    (i % 2 == 0 ? "F" : "H"),
                    "employe" + i + "@mail.com", "070000000" + i, "mdp" + i);
            boolean res = service.ajoutEmploye(e);
            printlnConsoleIHM("Ajout employé " + i + " : " + res);
        }
        Employe e = new Employe("NomEmployeFAUX", "PrenomEmployeFAUX",
                    "genre",
                    "employe" + 4 + "@mail.com", "070000000", "mdp");
            boolean res = service.ajoutEmploye(e);
            printlnConsoleIHM("Ajout employé RATE: " + res);
        
    }
    private static void testListerEmployes() throws ParseException {
        System.out.print("--------------TEST DE LISTAGE DES EMPLOYES--------------\n");
        printlnConsoleIHM("Liste des employes");
        Service service = new Service();
        List<Employe> employes = service.listerEmployes();
        int nb_employe=0;
        for (Employe e : employes){
            nb_employe++;
            printlnConsoleIHM("employe "+nb_employe+": "+ e.toString());
        }
    }
    private static void testAuthentifierUtilisateur() {
        System.out.print("--------------TEST : AUTHENTIFICATION--------------\n");
        Service service = new Service();

        // Test avec un client
        Object utilisateur1 = service.authentifierUtilisateur("client1@mail.com", "mdp1");
        if (utilisateur1 instanceof Client) {
            Client client = (Client) utilisateur1;
            printlnConsoleIHM("Client authentifié : " + client.getPrenom() + " " + client.getNom());
        } else {
            printlnConsoleIHM("Échec authentification client.");
        }

        // Test avec un employé
        Object utilisateur2 = service.authentifierUtilisateur("employe1@mail.com", "mdp1");
        if (utilisateur2 instanceof Employe) {
            Employe employe = (Employe) utilisateur2;
            printlnConsoleIHM("Employé authentifié : " + employe.getPrenom() + " " + employe.getNom());
        } else {
            printlnConsoleIHM("Échec authentification employé.");
        }

        // Test avec identifiants invalides
        Object utilisateur3 = service.authentifierUtilisateur("invalide@mail.com", "wrongpass");
        if (utilisateur3 == null) {
            printlnConsoleIHM("Aucun utilisateur trouvé avec ces identifiants (comme attendu).");
        } else {
            printlnConsoleIHM("ERREUR : un utilisateur a été authentifié avec des identifiants invalides !");
        }
    }

    private static void testDemandeConsultations() {
        System.out.print("--------------TEST DE DEMANDE DE CONSULTATION PAR UN CLIENT A UN MEDIUM--------------\n");
        Service service = new Service();

        List<Client> clients = service.listerClients();
        List<Medium> mediums = service.listerMediums();

        //boolean result = service.demandeConsultation(clients.get(i), mediums.get(i));
        //printlnConsoleIHM("Demande consultation " + i + " : " + result);
        
    }
    
    private static void testDemanderAideInspiration() {
        System.out.print("--------------TEST : DEMANDER AIDE INSPIRATION--------------\n");
        Service service = new Service();
        // Récupère un client avec un profil astral défini
        List<Client> clients = service.listerClients();
        Client client = clients.get(0); // le premier client inscrit
        try {
            List<String> predictions = service.demanderAideInspiration(client, 3, 4, 2);
            printlnConsoleIHM("Prédictions pour " + client.getPrenom() + " " + client.getNom() + " :");
            for (String p : predictions) {
                printlnConsoleIHM("• " + p);
            }
        } catch (Exception ex) {
            printlnConsoleIHM("Erreur lors de l'appel à l'API : " + ex.getMessage());
        }
    }
    
    private static void testTerminerConsultations() {
        System.out.print("--------------TEST TERMINER CONSULTATIONS--------------\n");
        Service service = new Service();
        List<Consultation> consultations = service.getConsultationsEnCours();
        if (!consultations.isEmpty()) {
            for (Consultation c : consultations) {
                boolean result = service.terminerConsultation(c);
                printlnConsoleIHM("Consultation terminée ? " + result);
            }
        }
    }

    private static void testAjouterCommentaireEmploye() {
        System.out.print("--------------TEST COMMENTAIRE EMPLOYE--------------\n");
        Service service = new Service();
        List<Consultation> consultations = service.listerConsultations();
        if (!consultations.isEmpty()) {
            Consultation c = consultations.get(0);
            boolean result = service.ajouterCommentaireEmploye(c, "Très bon contact avec le client.");
            printlnConsoleIHM("Commentaire ajouté ? " + result);
        }
    }

    private static void testAjouterAvisClient() {
        System.out.print("--------------TEST AVIS CLIENT--------------\n");
        Service service = new Service();
        List<Consultation> consultations = service.listerConsultations();
        if (!consultations.isEmpty()) {
            Consultation c = consultations.get(0);
            boolean result = service.ajouterAvisClient(c, "Merci beaucoup, très instructif !");
            printlnConsoleIHM("Avis ajouté ? " + result);
        }
    }

    private static void testListerConsultations() throws ParseException {
        System.out.print("--------------TEST DE LISTAGE DES CONSULTATIONS--------------\n");
        printlnConsoleIHM("Liste des consultations");
        Service service = new Service();
        List<Consultation> consultations = service.listerConsultations();
        int nb_consultation=0;
        for (Consultation c : consultations){
            nb_consultation++;
            printlnConsoleIHM("consultation "+nb_consultation+": "+ c.toString());
        }
    }
    
    private static void testVoirConsultationsPassees() {
        System.out.print("--------------TEST : CONSULTATIONS PASSÉES PAR UTILISATEUR--------------\n");
        Service service = new Service();

        // On prend les premiers éléments de chaque liste pour tester
        Client client = service.listerClients().get(0);
        Employe employe = service.listerEmployes().get(0);
        Medium medium = service.listerMediums().get(0);
        

        List<Consultation> consultationsClient = service.getHistoriqueConsultationsClient(client.getId());
        printlnConsoleIHM("Consultations passées du client " + client.getPrenom() + " " + client.getNom() + " :");
        printlnConsoleIHM(consultationsClient);
        if (consultationsClient != null){
            for (Consultation c : consultationsClient) {
                printlnConsoleIHM(c.toString());
            }
        }
        

        List<Consultation> consultationsEmploye = service.getHistoriqueConsultationsEmploye(employe.getId());
        printlnConsoleIHM("Consultations passées de l'employé " + employe.getPrenom() + " " + employe.getNom() + " :");
        if (consultationsEmploye != null){
            for (Consultation c : consultationsEmploye) {
                printlnConsoleIHM(c.toString());
            }
        }

        List<Consultation> consultationsMedium = service.getHistoriqueConsultationsMedium(medium.getId());
        printlnConsoleIHM("Consultations passées du médium " + medium.getDenomination() + " :");
        if (consultationsMedium != null) {
            for (Consultation c : consultationsMedium) {
                printlnConsoleIHM(c.toString());
            }
        }
    }
    
    private static void testStats() {
        System.out.print("--------------TEST STATS--------------\n");
        Service service = new Service();
        
        printlnConsoleIHM("--Top médium--");
        List<Object[]> top5Mediums = service.getTop5Mediums();
        for (Object[] row : top5Mediums) {
            Medium m = (Medium) row[0];
            Long nb = (Long) row[1];
            printlnConsoleIHM(m.getDenomination() + " : " + nb + " consultations");
        }
        printlnConsoleIHM("--Nb clients par employé--");
        List<Object[]> nbClientsByEmploye = service.getNbClientsByEmploye();
        for (Object[] row : nbClientsByEmploye) {
            Employe e = (Employe) row[0];
            Long nb = (Long) row[1];
            printlnConsoleIHM(e.getPrenom() + " " + e.getNom() + " : " + nb + " client(s) différents");
        }
        printlnConsoleIHM("--Nb de consultations sur les 7 derniers jours--");
        Long nbConsultationsSemaine = service.getNbConsultationSemaine();
        printlnConsoleIHM("Nb de consultations sur les 7 derniers jours: "+nbConsultationsSemaine);
    }

    public static void printlnConsoleIHM(Object o) {
        String BG_CYAN = "\u001b[46m";
        String RESET = "\u001B[0m";
        System.out.print(BG_CYAN);
        System.out.println(String.format("%-80s", o));
        System.out.print(RESET);
    }
}
