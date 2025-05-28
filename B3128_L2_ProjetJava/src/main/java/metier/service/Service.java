/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import metier.modele.Client;
import metier.modele.Employe;
import metier.modele.Medium;
import metier.modele.ProfilAstral;
import metier.modele.Consultation;
import dao.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import util.AstroNetApi;
import util.GeoNetApi;
import util.Message;

/**
 *
 * @author ygallard
 */
public class Service {

    protected ClientDao clientDao = new ClientDao();
    protected MediumDao mediumDao = new MediumDao();
    protected EmployeDao employeDao = new EmployeDao();
    protected ConsultationDao consultationDao = new ConsultationDao();

//-----------------services de creation d'entités-----------------
    public boolean inscriptionClient(Client client) {
        /*
        l'objet client en entrée est crée avant l'utilisation du service inscrireClient,
        les infos pour le creer sont recuperees d'un formulaire d'inscription   
        
         */
        boolean reussi;

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();

            //------CREATION PROFIL ASTRAL
            AstroNetApi boule_de_cristal = new AstroNetApi(); //instanciation de l'API aka boulde_de_cristal
            List<String> att_astraux; //création d'une liste pour contenir les attributs
            att_astraux = boule_de_cristal.getProfil(client.getPrenom(), client.getDate_naissance());
            //création du profil astral avec les attributs
            ProfilAstral profil_astral = new ProfilAstral(att_astraux.get(0), att_astraux.get(1), att_astraux.get(2), att_astraux.get(3));
            //set le profil astral du client
            client.setProfil_astral(profil_astral);

            //------CREATION COORD GPS
            client.setCoord_client(GeoNetApi.getLatLng(client.getAdresse()));

            clientDao.create(client);

            JpaUtil.validerTransaction();
            String objetMailToClient = "Bienvenue chez PREDICT'IF";
            String corpsMailToClient = "Bonjour " + client.getPrenom() + ", nous vous confirmons votre inscription au service PREDICT'IF.\nRendez-vous vite sur notre site pour ocnsulter votre profil astrologique et profiter des dons incroyables de nos mediums.";
            Message.envoyerMail("contact@predict.if", client.getMail(), objetMailToClient, corpsMailToClient);
            reussi = true;
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            String objetMailToClient = "Echec de l'inscription chez PREDCT'IF";
            String corpsMailToClient = "Bonjour " + client.getPrenom() + ",votre inscription au service PREDICT'IF a malencontreusement échoué...\nMerci de recommencer ultérieurement.";
            Message.envoyerMail("contact@predict.if", client.getMail(), objetMailToClient, corpsMailToClient);
            reussi = false;
        } finally {
            JpaUtil.fermerContextePersistance();

        }

        return reussi;
    }

    public boolean ajoutMedium(Medium medium) {
        //Fonction qui ajoute un medium et le fait persister dans la BD
        //fonctionne comme inscrireClients mais sans le profil astral
        boolean reussi;

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            mediumDao.create(medium);
            JpaUtil.validerTransaction();
            reussi = true;
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            reussi = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return reussi;
    }

    public boolean ajoutEmploye(Employe employe) {
        //Fonction qui ajoute un medium et le fait persister dans la BD
        //fonctionne comme inscrireClients mais sans le profil astral
        boolean reussi;

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            employeDao.create(employe);
            JpaUtil.validerTransaction();
            reussi = true;
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            reussi = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return reussi;
    }

    public boolean demandeConsultation(Client client, Medium medium) {
        
        boolean resultat = false;

        System.out.println(medium);
        try {
            JpaUtil.creerContextePersistance();

            Employe employe = employeDao.getMatchingEmploye(medium);
            System.out.println(employe);
            if (employe != null) {

                Consultation newConsultation = new Consultation(client, medium, employe);
                Date dateDemande = new Date();
                newConsultation.setDate_debut(dateDemande);

                JpaUtil.ouvrirTransaction();
                consultationDao.create(newConsultation);

                employe.setDisponible(false);
                employeDao.update(employe);
                
                JpaUtil.validerTransaction();

                //SimpleDateFormat
                
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy 'à' HH:mm");

                // envoie des notifications
                String messageToEmploye = "Bonjour " + employe.getPrenom() + ".\nConsultation requise pour " + client.getPrenom() + " " + client.getNom()
                        + ". Médium à incarner: " + medium.getDenomination() + ".";
                String messageToClient = "Bonjour " + client.getPrenom() + ". J'ai bien reçu votre demande de consultation du "
                        + dateFormat.format(dateDemande)
                        + ". Vous pouvez dès à présent me contacter au " + employe.getTel() + ". À tout de suite!\nMédiumiquement vôtre, " + medium.getDenomination() + ".";
                Message.envoyerNotification(employe.getTel(), messageToEmploye);
                Message.envoyerNotification(client.getTel(), messageToClient);

                resultat = true;
            }
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            //envoyer une notification au client
            String messageToClient = "Bonjour " + client.getPrenom() + ".\nJe suis désolé, mais je ne suis pas disponible pour le moment. Je vous invite à me recontacter ultérieurement.\nMerci de votre compréhension.";
            Message.envoyerNotification(client.getTel(), messageToClient);
            resultat = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return resultat;
    }

//-----------------Services de recherche d'instances-----------------
    public Consultation findConsultationEnCours(Employe employe) {
        //utilise le EmployeDao
        Consultation c;

        try {
            JpaUtil.creerContextePersistance();
            c = employeDao.findConsultationEnCours(employe);
        } catch (Exception ex) {
            c = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return c;
    }

    public Client findClientById(Long id) {
        //utilise le ClientDao
        Client c;

        try {
            JpaUtil.creerContextePersistance();
            c = clientDao.findById(id);
        } catch (Exception ex) {
            c = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return c;
    }

    public Client findClientByMail(String mail) {
        //utilise le ClientDao
        Client c;

        try {
            JpaUtil.creerContextePersistance();
            c = clientDao.findByMail(mail);
        } catch (Exception ex) {
            c = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return c;
    }

    public Client findClientByConsultation(Consultation consultation) {
        //utilise le ClientDao
        Client c;

        try {
            JpaUtil.creerContextePersistance();
            c = clientDao.findByConsultation(consultation);
        } catch (Exception ex) {
            c = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return c;
    }

    public Medium findMediumById(Long id) {
        //utilise le MediumDao
        Medium m;

        try {
            JpaUtil.creerContextePersistance();
            m = mediumDao.findById(id);
        } catch (Exception ex) {
            m = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return m;
    }

    public Employe findEmployeById(Long id) {
        //utilise le EmployeDao
        Employe e;

        try {
            JpaUtil.creerContextePersistance();
            e = employeDao.findById(id);
        } catch (Exception ex) {
            e = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return e;
    }

    public Employe findEmployeByMail(String mail) {
        //utilise le EmployeDao
        Employe e;

        try {
            JpaUtil.creerContextePersistance();
            e = employeDao.findByMail(mail);
        } catch (Exception ex) {
            e = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return e;
    }

    public Employe findEmployeByConsultation(Consultation consultation) {
        //utilise le EmployeDao
        Employe e;

        try {
            JpaUtil.creerContextePersistance();
            e = employeDao.findByConsultation(consultation);
        } catch (Exception ex) {
            e = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return e;
    }

    public Consultation findConsultationById(Long id) {
        //utilise le  ConsultationDao
        Consultation c;

        try {
            JpaUtil.creerContextePersistance();
            c = consultationDao.findById(id);
        } catch (Exception ex) {
            c = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return c;
    }

//-----------------services stats -----------------
    public List<Object[]> getTop5Mediums() {

        try {
            JpaUtil.creerContextePersistance();
            return consultationDao.getTop5Mediums();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }

    public List<Object[]> getNbConsulByMediums() {

        try {
            JpaUtil.creerContextePersistance();
            return consultationDao.getNbConsulByMediums();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }

    public List<Object[]> getNbClientsByEmploye() {

        try {
            JpaUtil.creerContextePersistance();
            return consultationDao.getNbClientsByEmploye();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }

    public Long getNbConsultationSemaine() {

        try {
            JpaUtil.creerContextePersistance();
            return consultationDao.getNbConsultationSemaine();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }

//-----------------services autres-----------------
    public boolean terminerConsultation(Consultation consultation) {
        Boolean resultat;

        try {
            JpaUtil.creerContextePersistance();
            if (consultation == null || consultation.getDate_fin() != null) {
                return false;
            }
            JpaUtil.ouvrirTransaction();
            consultation.setDate_fin(new Date());

            // rends l'employé disponible
            if (consultation.getEmploye() != null) {
                Employe employe = consultation.getEmploye();
                employe.setDisponible(true);
                employeDao.update(employe); // Mise à jour de l'employé dans la base de données
            }
            consultationDao.update(consultation);

            JpaUtil.validerTransaction();
            resultat = true;
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            resultat = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    public boolean ajouterCommentaireEmploye(Consultation consultation, String commentaire_employe) {
        //ajouter/modifier commentaire employe
        Boolean resultat;

        try {
            JpaUtil.creerContextePersistance();
            if (consultation == null) {
                resultat = false;
            } else {

                JpaUtil.ouvrirTransaction();
                consultation.setCommentaire_employe(commentaire_employe);
                consultationDao.update(consultation);

                JpaUtil.validerTransaction();
                resultat = true;
            }
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            resultat = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    public boolean ajouterAvisClient(Consultation consultation, String avis_client) {
        //ajouter/modifier l'avis client d'une consultation
        Boolean resultat;

        try {
            JpaUtil.creerContextePersistance();
            if (consultation == null) {
                resultat = false; // Invalid consultation
            } else {
                JpaUtil.ouvrirTransaction();

                // Set the client's feedback
                consultation.setAvis_client(avis_client);

                // Persist changes
                consultationDao.update(consultation);

                JpaUtil.validerTransaction();
                resultat = true;
            }
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            resultat = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    public List<String> demanderAideInspiration(Client c, int amour, int sante, int travail) throws IOException {
        List<String> predictions;
        AstroNetApi bouleDeCristal = new AstroNetApi();

        predictions = bouleDeCristal.getPredictions(c.getProfil_astral().getCouleur(), c.getProfil_astral().getAnimal(), amour, sante, travail);
        return predictions;
    }

    public Object authentifierUtilisateur(String mail, String mdp) {

        try {
            JpaUtil.creerContextePersistance();
            Client client = clientDao.findByMail(mail);
            if (client != null && client.getMdp().equals(mdp)) {
                return client; // Authentifié en tant que client
            }

            Employe employe = employeDao.findByMail(mail);
            if (employe != null && employe.getMdp().equals(mdp)) {
                return employe; // Authentifié en tant qu'employé
            }

            return null; // Aucun utilisateur trouvé ou mot de passe incorrect
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }

//-----------------Lister des elements-----------------
    //LISTER DES ELEMENTS 
    public List<Consultation> getHistoriqueConsultationsClient(Long clientId) {

        try {
            JpaUtil.creerContextePersistance();
            return clientDao.getHistoriqueConsultation(clientId);
        } catch (Exception ex) {
            return null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }

    }

    public List<Consultation> getHistoriqueConsultationsEmploye(Long employeId) {

        try {
            JpaUtil.creerContextePersistance();
            return employeDao.getHistoriqueConsultation(employeId);
        } catch (Exception ex) {
            return null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }

    public List<Consultation> getHistoriqueConsultationsMedium(Long mediumId) {

        try {
            JpaUtil.creerContextePersistance();
            return mediumDao.getHistoriqueConsultation(mediumId);
        } catch (Exception ex) {
            return null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }

    public List<Client> listerClients() {
        List<Client> clients;

        try {
            JpaUtil.creerContextePersistance();
            clients = clientDao.findAll();
        } catch (Exception ex) {
            clients = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return clients;
    }

    public List<Medium> listerMediums() {
        List<Medium> mediums;

        try {
            JpaUtil.creerContextePersistance();
            mediums = mediumDao.findAll();
        } catch (Exception ex) {
            mediums = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return mediums;
    }

    public List<Employe> listerEmployes() {
        List<Employe> employes;

        try {
            JpaUtil.creerContextePersistance();
            employes = employeDao.findAll();
        } catch (Exception ex) {
            employes = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return employes;
    }

    public List<Consultation> listerConsultations() {
        List<Consultation> consultations;

        try {
            JpaUtil.creerContextePersistance();
            consultations = consultationDao.findAll();
        } catch (Exception ex) {
            consultations = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return consultations;
    }

    public List<Consultation> getConsultationsEnCours() {
        List<Consultation> consultations;

        try {
            JpaUtil.creerContextePersistance();
            consultations = consultationDao.getConsultationsEnCours();
        } catch (Exception ex) {
            consultations = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return consultations;
    }

}
