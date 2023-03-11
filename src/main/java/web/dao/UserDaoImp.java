package web.dao;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import web.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
@Transactional(readOnly = true)
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void addUser(User user) {
        em.persist(user);
    }

    @Override
    @Transactional
    public void updateUser(int id, User user) {
        em.merge(user);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        em.remove(getUserById(id));
    }

    @Override
    public User getUserById(int id) {
        TypedQuery<User> q =  em.createQuery(
                "select u from User u where u.id = :id", User.class
        );
        q.setParameter("id", id);
        return q.getResultList().stream().findAny().orElse(null);
    }

    @Override
    public List<User> getUsers() {
        return em.createQuery(
                "select u from User u", User.class
        ).getResultList();
    }
}
