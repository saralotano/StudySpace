package session;

import entity.Admin;
import javax.ejb.Local;
import java.util.List;

@Local
public interface AdminLocal {
    void create(Admin admin);

    void edit(Admin admin);

    void remove(Admin admin);

    Admin find(Object id);

    List<Admin> findAll();

    List<Admin> findRange(int[] range);

    int count();

    Admin login(String username, String password);

}
