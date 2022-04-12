package session;

import entity.Room;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless(name = "RoomEJB")
public class RoomBean extends AbstractFacade<Room> implements RoomLocal{

    @PersistenceContext(unitName = "StudySpacePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RoomBean() {
        super(Room.class);
    }

    @Override
    public List<String> findHowManyRooms(){
        TypedQuery<String> query = em.createNamedQuery("Room.findHowManyRooms", String.class);
        return query.getResultList();
    }

    @Override
    public List<Room> findByDate(java.util.Date date){
        TypedQuery<Room> query = em.createNamedQuery("Room.findByDate", Room.class);
        query.setParameter("date", date);
        return query.getResultList();
    }

    @Override
    public Integer findByIdroomDateTimeslot(Integer idroom, java.util.Date date, String timeslot){
        TypedQuery<Room> query = em.createNamedQuery("Room.findByIdroomDateTimeslot", Room.class);
        query.setParameter("idroom", idroom);
        query.setParameter("date", date);
        query.setParameter("timeslot", timeslot);
        return query.getResultList().size();
    }
}
