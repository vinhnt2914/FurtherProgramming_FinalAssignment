package org.example.model.provider;

import jakarta.persistence.*;
import org.example.model.User;

@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Provider extends User {

    public Provider(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Provider() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
