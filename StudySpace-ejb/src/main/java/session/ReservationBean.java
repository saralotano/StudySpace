package session;

import entity.Reservation;
import entity.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless(name = "ReservationEJB")
public class ReservationBean extends AbstractFacade<Reservation> implements ReservationLocal {

    @PersistenceContext(unitName = "StudySpacePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReservationBean() {
        super(Reservation.class);
    }

    @Override
    public List<Reservation> findByIduser(User user){
        TypedQuery<Reservation> query = em.createNamedQuery("Reservation.findByIduser", Reservation.class);
        query.setParameter("iduser", user.getIduser());
        return query.getResultList();
    }

    public Reservation findByIduserIdroom(int iduser, int idroom){
        TypedQuery<Reservation> query = em.createNamedQuery("Reservation.findByIduserIdroom", Reservation.class);
        query.setParameter("iduser", iduser);
        query.setParameter("idroom", idroom);
        Reservation r;
        try{
            r = query.getResultList().get(0);
        }catch(IndexOutOfBoundsException e){
            r = null;
        }
        return r;
    }

}
