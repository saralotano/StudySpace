package session;

import entity.Admin;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless(name = "AdminEJB")
public class AdminBean extends AbstractFacade<Admin> implements AdminLocal{

    @PersistenceContext(unitName = "StudySpacePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdminBean() {
        super(Admin.class);
    }

    @Override
    public Admin login(String username, String password) {
        TypedQuery<Admin> query = em.createNamedQuery("Admin.findByUsernameAndPassword", Admin.class);
        query.setParameter("username", username);
        query.setParameter("password", password);

        try {
            Admin c = query.getSingleResult();
            return c;
        }
        catch (NoResultException e) {
            return null;
        }
    }
}
