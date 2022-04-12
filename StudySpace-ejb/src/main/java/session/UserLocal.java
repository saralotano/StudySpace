package session;

import entity.User;
import javax.ejb.Local;
import java.util.List;


@Local
public interface UserLocal {

    void create(User customer);

    void edit(User customer);

    void remove(User customer);

    User find(Object id);

    List<User> findAll();

    List<User> findRange(int[] range);

    int count();

    User login(String username, String password);

}
