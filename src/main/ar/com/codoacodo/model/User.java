package main.ar.com.codoacodo.model;

import java.sql.Timestamp;

public class User {

    private Integer id;
    private String email;
    private String password;
    private Timestamp date;
    private boolean active;

    public User() {}

    @Override
    public String toString() {
        String safer = "*".repeat(getPassword().length());
        return String.format(
                "[ID=%s, EMAIL=%s, PASSWORD=%s, DATE=%s, ACTIVE=%s]",
                (id > 0? id : "AUTO_INCREMENT"),
                email,
                safer,
                date,
                (active ? "si" : "no")
        );
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
