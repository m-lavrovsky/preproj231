package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAOHibernateImpl implements UserDAO {
    public UserDAOHibernateImpl() {
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(String name, String lastName, int age) {
        entityManager.persist(entityManager.merge(new User(name, lastName, age)));
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(entityManager.merge(user));
    }

    @Override
    public void removeUser(User user) {
        entityManager.remove(entityManager.merge(user));
    }

    @Override
    public void editUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public User getById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public boolean hasId (Long id) {
        try {
            User user = entityManager.createQuery("SELECT u FROM User u where u.id = "+id, User.class).getSingleResult();
            return true;
        }
        catch (NoResultException e) {
            return false;
        }
    }
}

