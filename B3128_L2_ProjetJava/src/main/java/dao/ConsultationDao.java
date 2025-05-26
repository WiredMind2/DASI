/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import dao.JpaUtil;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import metier.modele.Consultation;
/**
 *
 * @author ygallard
 */
public class ConsultationDao {
    

    public void create(Consultation consultation) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(consultation);
    }
    public List<Consultation> findAll() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Consultation> query = em.createQuery("SELECT c FROM Consultation c", Consultation.class);
        return query.getResultList();
    }
    public Consultation findById(Long id){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.find(Consultation.class,id);
    }
    public List<Object[]> getTop5Mediums() {    //redondant par rapport à getNbConsulMediums
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.createQuery(
            "SELECT c.medium, COUNT(c) " +
            "FROM Consultation c " +
            "GROUP BY c.medium " +
            "ORDER BY COUNT(c) DESC",
            Object[].class
        ).setMaxResults(5).getResultList();
    }
    public List<Object[]> getNbConsulByMediums() {    
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.createQuery(
            "SELECT c.medium, COUNT(c) " +
            "FROM Consultation c " +
            "GROUP BY c.medium " +
            "ORDER BY COUNT(c) DESC",
            Object[].class
        ).getResultList();
    }
    

    
    public List<Object[]> getNbClientsByEmploye() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.createQuery(
            "SELECT c.employe, COUNT(DISTINCT c.client) " +       //passage par id car le group by ne marche pas sur
            "FROM Consultation c " +                //des colonnes BLOB un champ complexe (ici la colonne coord)
            "GROUP BY c.employe " +
            "ORDER BY COUNT(DISTINCT c.client) DESC", Object[].class
        ).setMaxResults(5).getResultList();
    }

    public List<Consultation> getConsultationsEnCours() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Consultation> query = em.createQuery("SELECT c FROM Consultation c WHERE c.date_fin is null", Consultation.class);
        return query.getResultList();
    }

    public List<Consultation> getConsultationsTerminees() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.createQuery("SELECT c FROM Consultation c WHERE c.date_fin IS NOT NULL", Consultation.class)
             .getResultList();
    }
    public long getNbConsultationSemaine() {
        EntityManager em = JpaUtil.obtenirContextePersistance();

        // Date il y a 7 jours
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        Date sevenDaysAgoDate = java.sql.Timestamp.valueOf(sevenDaysAgo);

        return em.createQuery(
                "SELECT COUNT(c) FROM Consultation c WHERE c.date_fin >= :sevenDaysAgo", Long.class)
                .setParameter("sevenDaysAgo", sevenDaysAgoDate)
                .getSingleResult();
    }

    //update
    public Consultation update(Consultation consultation) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.merge(consultation);
    }

}

