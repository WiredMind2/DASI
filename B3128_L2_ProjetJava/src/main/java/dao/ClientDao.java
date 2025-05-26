    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import dao.JpaUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import metier.modele.Client;
import metier.modele.Consultation;
/**
 *
 * @author ygallard
 */
public class ClientDao {
    

    public void create(Client client) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(client);
    }
    public List<Client> findAll() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c", Client.class);
        return query.getResultList();
    }
    public Client findById(Long id){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        Client client = em.find(Client.class,id);
        if (client == null)
            System.out.println("C'EST NUUUUUUUUUUUUUUUUL");
        return client;
    }
    
    public Client findByMail(String mail) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        try {
            return em.createQuery("SELECT c FROM Client c WHERE c.mail = :mail", Client.class)
                     .setParameter("mail", mail)
                     .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }
    
    public Client findByConsultation(Consultation consultation) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        try {
            return em.createQuery("SELECT c FROM Client c WHERE c.consultation = :consultation", Client.class)
                     .setParameter("consultation", consultation)
                     .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }
    public List<Consultation> getHistoriqueConsultation(Long clientId) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        try{
            return em.createQuery("SELECT c FROM Consultation c WHERE c.client.id = :clientId ORDER BY c.date_debut DESC",Consultation.class)
                    .setParameter("clientId",clientId).getResultList();            
        }
        catch (Exception ex) {
            return null;
        }
    }

    public Client update(Client client) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.merge(client);
    }
}
