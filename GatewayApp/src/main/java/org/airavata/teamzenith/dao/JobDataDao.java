package org.airavata.teamzenith.dao;

import java.util.ArrayList;
import java.util.LinkedList;
/**
 * This class is used to access data for the User entity.
 * Repository annotation allows the component scanning support to find and 
 * configure the DAO wihtout any XML configuration and also provide the Spring 
 * exceptiom translation.
 * Since we've setup setPackagesToScan and transaction manager on
 * DatabaseConfig, any bean method annotated with Transactional will cause
 * Spring to magically call begin() and commit() at the start/end of the
 * method. If exception occurs it will also call rollback().
 */
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
@Repository
@Transactional
public class JobDataDao {
  
  /**
   * Save the user in the database.
   */
  public void create(JobData job) {
    entityManager.merge(job);
    entityManager.flush();
    return;
  }
  
  /**
   * Delete the user from the database.
   */
  public void delete(JobData job) {
    if (entityManager.contains(job))
      entityManager.remove(job);
    else
      entityManager.remove(entityManager.merge(job));
    return;
  }
  
  /**
   * Return all the users stored in the database.
   */
  @SuppressWarnings("unchecked")
  public List getAll() {
    return entityManager.createQuery("from TZ_JOB_DATA").getResultList();
  }
  
  /**
   * Return the user having the passed email.
   */
  public List<JobData> getByUser(String uName) {
    List ljd=entityManager.createQuery(
        "SELECT j from UserData AS ud, UserJobData AS u,  JobData as j "
        + "WHERE u.UserId=ud.UserId AND u.JobId=j.JobId and  ud.UserName=:un")	
        .setParameter("un", uName)
        .getResultList();
//    List<String> lst = new ArrayList<>();
//    for(Object o : ljd) {
//    	//JobData resjd=new JobData();
//    	System.out.println(String.valueOf(o));
//
//    }
    return ljd;
  }

  public List<JobData> getByUserEmail(String uEmail) {
	    List ljd=entityManager.createQuery(
	        "select j from UserData AS u, UserJobData AS i, JobData AS j WHERE i.UserId=u.UserId AND i.JobId=j.JobId AND u.Email=:ue")	
	        .setParameter("ue", uEmail)
	        .getResultList();
//	    List<String> lst = new ArrayList<>();
//	    for(Object o : ljd) {
//	    	//JobData resjd=new JobData();
//	    	System.out.println(String.valueOf(o));
	//
//	    }
	    return ljd;
	  }
  public Long getJobName(String uName, String jName) {
	    Long ljd=(Long)entityManager.createQuery(
	        "select count(*) from UserData tud, UserJobData tuji, JobData tjd "
	        + "WHERE tud.UserId=tuji.UserId AND tuji.JobId=tjd.JobId AND tud.UserName=:un AND tjd.JobName=:jn")	
	        .setParameter("un", uName)
	        .setParameter("jn", jName)
	        .getSingleResult();

	    return ljd;
	  }
  
  /**
   * Return the user having the passed id.
   */
  public UserJobData getById(long id) {
    return entityManager.find(UserJobData.class, id);
  }

  /**
   * Update the passed user in the database.
   */
  public void update(UserJobData job) {
    entityManager.merge(job);
    return;
  }

  // Private fields
  
  // An EntityManager will be automatically injected from entityManagerFactory
  // setup on DatabaseConfig class.
  @PersistenceContext
  private EntityManager entityManager;
  
}