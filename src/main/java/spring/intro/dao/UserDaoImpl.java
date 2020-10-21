package spring.intro.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import spring.intro.exception.DataProcessingException;
import spring.intro.model.User;

@Repository
public class UserDaoImpl implements UserDao {
    private final SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add new user: " + user, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> listUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User ", User.class).getResultList();
        }
    }

    @Override
    public User getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User WHERE id = :id", User.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }
    }
}
