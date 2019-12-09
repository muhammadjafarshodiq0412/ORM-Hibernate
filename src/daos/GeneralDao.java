/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author USER
 */
public class GeneralDao<E> implements IDao<E> {

    private Session session;
    private Transaction transaction;
    private SessionFactory sessionFactory;
    public GeneralDao fd;

    public GeneralDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override

     public E save(E entity) {
     return execute(entity, null, "SaveOrUpdate",null,null,null,null,null);
     }

    @Override
  
    public E delete(E entity) {
          return execute(entity, null, "Delete",null,null,null,null,null);
    }

    @Override
 
     public List<E> select(String entity) {
            //  List<E> data = new ArrayList<>();
        return (List<E>) execute((E) entity, null, "Select",null,null,null,null,null);
     }

    @Override
         public List<E> search(String table, String field, String key) {
            return (List<E>) execute(null, null, "Search",table,field,key,null,null);
     }

    @Override
    public E selectByField(String tabel, String field1, String field2, String fname, String lname) {
       return (E) execute(null,null, "SelectByField2",tabel,field1,fname,field2,lname);
    }

    @Override
    public E selectByField(String table, String field, String key) {
          return (E) execute(null, null,"SelectByField",table,field,key,null,null);
    }


    public E execute(E entity, String query, String fungsi, String tabel, String field, String key, String field2, String key2) {
      
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            if (fungsi.equals("SaveOrUpdate")) {
                session.saveOrUpdate(entity);
            } else if (fungsi.equals("Delete")) {
                session.delete(entity);
            }
            else if(fungsi.equals("Select")){
          return (E) session
                    .createQuery("from " + entity + " order by 1")
                    .list();
            }
            else if(fungsi.equals("Search")){
                return (E) session.createQuery("from " + tabel + " where " + field + "  like :keys")
                    .setString("keys", "%" + key + "%")
                    .list();
            }
            else if ((fungsi.equals("SelectByField"))) {
                 return (E) session.createQuery("from " + tabel + " where " + field + " like :key")
                    .setString("key", key)
                    .uniqueResult();
            }
            else if ((fungsi.equals("SelectByField2"))) {
          return (E) session.createQuery("from " + tabel + " where " + field + "  like :key and " + field2 + " like :keys")
                    .setString("key", key)
                    .setString("keys", key2)
                    .uniqueResult();
            }

            transaction.commit();
           // return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return entity;
    }

}
