package session;

import entity.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless(name = "UserEJB")
public class UserBean extends AbstractFacade<User> implements UserLocal {
    @PersistenceContext(unitName = "StudySpacePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserBean() {
        super(User.class);
    }

    @Override
    public User login(String username, String password) {
        TypedQuery<User> query = em.createNamedQuery("User.findByUsernameAndPassword", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);

        try {
            User c = query.getSingleResult();
            return c;
        }
        catch (NoResultException e) {
            return null;
        }
    }
}