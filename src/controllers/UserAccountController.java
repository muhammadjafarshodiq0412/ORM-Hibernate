/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.GeneralDao;
import models.UserAccount;
import tools.BCrypt;
import tools.HibernateUtil;

/**
 *
 * @author ASUS
 */
public class UserAccountController<E> {
    
    private GeneralDao dao;

      public UserAccountController() {
          this.dao = new GeneralDao(HibernateUtil.getSessionFactory());
    }
    
    public UserAccountController(GeneralDao dao) {
        this.dao = dao;
    }
    
    public boolean login(String key, String password){
        UserAccount account = (UserAccount) this.dao.selectByField("UserAccount", "username", key);
          if (BCrypt.checkpw(password, account.getPassword())) {
                return true;
            } else {
                return false;
            }
    }

     public String save(String id, String name, String password) {
        return this.dao.save(new UserAccount(Integer.parseInt(id), name,BCrypt.hashpw(password, BCrypt.gensalt(5)))) ? 
                "Success to Save UserAccount" : "Failed to Save UserAccount";
    }
  
    
    
}
