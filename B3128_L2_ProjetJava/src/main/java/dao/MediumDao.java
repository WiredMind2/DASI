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
import metier.modele.Consultation;
import metier.modele.Medium;
/**
 *
 * @author ygallard
 */
public class MediumDao {
    

    public void create(Medium medium) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(medium);
    }
    public List<Medium> findAll() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Medium> query = em.createQuery("SELECT m FROM Medium m", Medium.class);
        return query.getResultList();
    }
    public Medium findById(Long id){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.find(Medium.class,id);
    }
    public List<Consultation> getHistoriqueConsultation(Long mediumId) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        try{
            return em.createQuery("SELECT c FROM Consultation c WHERE c.medium.id = :mediumId ORDER BY c.date_debut DESC",Consultation.class)
                    .setParameter("mediumId",mediumId).getResultList();            
        }
        catch (Exception ex) {
            return null;
        }
    }
}