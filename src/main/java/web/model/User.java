package web.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column (name = "lastname")
    private String lastname;

    private Byte age;

    public User() {
    }

    public User(String name, String lastname, int age) {
        this.name = name;
        this.lastname = lastname;
        this.age = (byte) age;
    }

    public User(Long id, String name, String lastname, int age) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.age = (byte) age;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastName) {
        this.lastname = lastName;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public String toString() {
        return "id " + getId()+" : " + getName() + " " + getLastname() + ", "+getAge()+" years old";
    }
}

