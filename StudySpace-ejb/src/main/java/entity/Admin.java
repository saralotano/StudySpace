package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "admin", schema = "studyspace")

@NamedQueries({
        @NamedQuery(name = "Admin.findByUsernameAndPassword", query = "SELECT u FROM Admin u WHERE u.username = :username AND u.password = :password")
})

public class Admin implements Serializable {
    private int idadmin;
    private String username;
    private String password;

    @Id
    @Column(name = "idadmin", nullable = false)
    public int getIdadmin() {
        return idadmin;
    }

    public void setIdadmin(int idadmin) {
        this.idadmin = idadmin;
    }

    
    @Basic
    @Column(name = "username", nullable = false, length = 45)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 45)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin that = (Admin) o;
        return idadmin == that.idadmin && Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idadmin, username, password);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "idadmin=" + idadmin +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
