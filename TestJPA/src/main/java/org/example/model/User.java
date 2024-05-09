package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    @Column(nullable = false, unique = true)
    protected String username;
    @Column( nullable = false)
    protected String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
