package com.ra.model.repository;


import com.ra.model.entity.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;


@Repository
public class PersonRepositoryImpl implements PersonRepository {
    @Autowired
    // tiem SessionFactory
    private SessionFactory sessionFactory;
    @Autowired
    private static EntityManager entityManager;

    @Override
    public List<Person> findAll() {
        List<Person> list = new ArrayList<>();
        // khoi tao doi tuong sesssion
        Session session = sessionFactory.openSession();
        try {
            list = session.createQuery("from Person").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public Person saveOrUpdate(Person person) {
        // khởi tạo các đối tượng để quản lí giao dịch
        Session session = null;
        Transaction transaction = null;
        try {
            //khởi tạo session
            session = sessionFactory.openSession();
            // bắt đầu 1 giao dịch
            transaction = session.beginTransaction();
            if (person.getId() == null) {
                // chúc năng thêm mới
                session.save(person);
            } else {
                // chức năng update
                // lấy đối tượng cũ cần sửa ra
                Person old = findById(person.getId());
                if (person.getAvatar() == null) {
                    person.setAvatar(old.getAvatar());
                }
                old.copy(person);
                session.saveOrUpdate(old);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.isActive();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return person;
    }

    @Override
    public void delete(Long id) {
        Session session = null ;
        Transaction transaction = null ;
        try{
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.delete(findById(id));
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            if(transaction!= null){
                transaction.isActive();
            }
        }finally {
            if(session != null){
                session.close();
            }
        }

    }

    @Override
    public Person findById(Long id) {
        // sử dụng các phương thức của đối tượng 1 cách tự động
        TypedQuery<Person> type = entityManager.createQuery("select p from Person as p where p.id =:id ", Person.class);
        type.setParameter("id", id);
        // lấy về 1 đối tượng
        Person p = type.getSingleResult();
        return p;
    }
}
