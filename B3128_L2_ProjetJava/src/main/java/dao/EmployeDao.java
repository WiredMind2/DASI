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
import metier.modele.Employe;
import metier.modele.Medium;

/**
 *
 * @author ygallard
 */
public class EmployeDao {
    public void create(Employe employe) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(employe);
    }
    public List<Employe> findAll() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Employe> query = em.createQuery("SELECT e FROM Employe e", Employe.class);
        return query.getResultList();
    }
    public Employe findById(Long id){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.find(Employe.class,id);
    }
    
    public Employe findByMail(String mail) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        try {
            return em.createQuery("SELECT e FROM Employe e WHERE e.mail = :mail", Employe.class)
                     .setParameter("mail", mail)
                     .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public Employe findByConsultation(Consultation consultation) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        try {
            return em.createQuery("SELECT e FROM Employe e WHERE e.consultation = :consultation", Employe.class)
                     .setParameter("consultation", consultation)
                     .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public Consultation findConsultationEnCours(Employe employe) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        try {
            return em.createQuery("SELECT c FROM Consultation c WHERE c.employe.id = :employeId AND c.date_fin IS NULL", Consultation.class)
                     .setParameter("employeId", employe.getId())
                     .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }
    
    public Employe getMatchingEmploye(Medium m){
        //choisi l'employe qui a le moins de consultations, qui est disponible et qui a le medium genre
        EntityManager em = JpaUtil.obtenirContextePersistance();
        try{
            return em.createQuery("SELECT e FROM Employe e WHERE e.disponible = true AND e.genre = :genre ORDER BY (SELECT COUNT(c) FROM Consultation c WHERE c.employe.id = e.id)",Employe.class)
                    .setParameter("genre",m.getGenre()).setMaxResults(1).getSingleResult();            
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public List<Consultation> getHistoriqueConsultation(Long employeId) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        try{
            return em.createQuery("SELECT c FROM Consultation c WHERE c.employe.id = :employeId ORDER BY c.date_debut DESC",Consultation.class)
                    .setParameter("employeId",employeId).getResultList();            
        }
        catch (Exception ex) {
            return null;
        }
    }

    public Employe update(Employe employe) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.merge(employe); 
    }
}
