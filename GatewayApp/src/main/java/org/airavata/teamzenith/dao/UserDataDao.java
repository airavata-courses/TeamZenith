package org.airavata.teamzenith.dao;

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
public class UserDataDao {
  
  /**
   * Save the user in the database.
   */
  public void create(UserData user) {
    entityManager.persist(user);
    return;
  }
  
  /**
   * Delete the user from the database.
   */
  public void delete(UserData user) {
    if (entityManager.contains(user))
      entityManager.remove(user);
    else
      entityManager.remove(entityManager.merge(user));
    return;
  }
  
  /**
   * Return the user having the user name.
   */
  public Long getByUsername(String username) {
    Long udd= (Long)entityManager.createQuery(
        "SELECT count(*) from UserData where UserName= :jn")
        .setParameter("jn", username).getSingleResult();        
  return udd;
  }
  /**
   * Return all the users stored in the database.
   */
  
  public Long getUserId(String username) {
	    Long udd= (Long)entityManager.createQuery(
	        "SELECT UserId from UserData where UserName= :jn")
	        .setParameter("jn", username).getSingleResult();        
	  return udd;
	  }
  @SuppressWarnings("unchecked")
  public List getAll() {
    return entityManager.createQuery("from TZ_USER_DATA").getResultList();
  }
  
  /**
   * Return the user having the passed email.
   */
  public UserData getByEmail(String email) {
    return (UserData) entityManager.createQuery(
        "from TZ_USER_DATA where Email = :email")
        .setParameter("email", email)
        .getSingleResult();
  }

  /**
   * Return the user having the passed id.
   */
  public UserData getById(long id) {
    return entityManager.find(UserData.class, id);
  }

  /**
   * Update the passed user in the database.
   */
  public void update(UserData user) {
    entityManager.merge(user);
    return;
  }

  // Private fields
  
  // An EntityManager will be automatically injected from entityManagerFactory
  // setup on DatabaseConfig class.
  @PersistenceContext
  private EntityManager entityManager;
  
}