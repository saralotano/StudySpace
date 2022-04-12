package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity

@Table(name = "user", schema = "studyspace")
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
        , @NamedQuery(name = "User.findByIduser", query = "SELECT u FROM User u WHERE u.iduser = :iduser")
        , @NamedQuery(name = "User.findByUsernameAndPassword", query = "SELECT u FROM User u WHERE u.username = :username AND u.password = :password")
        , @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User  u WHERE u.username = :username")
})

public class User implements Serializable {
    private int iduser;
    private String username;
    private String password;

    @Id
    @Column(name = "iduser", nullable = false)
    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
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
        User that = (User) o;
        return iduser == that.iduser && Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iduser, username, password);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "iduser=" + iduser +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
