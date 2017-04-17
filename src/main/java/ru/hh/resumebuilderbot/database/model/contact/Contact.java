package ru.hh.resumebuilderbot.database.model.contact;

import ru.hh.resumebuilderbot.database.model.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private ContactType type;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Contact() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ContactType getType() {
        return type;
    }

    public void setType(ContactType type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contact contact = (Contact) o;
        return Objects.equals(id, contact.id) &&
                Objects.equals(type, contact.type) &&
                Objects.equals(user.getId(), contact.user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type.getId(), user.getId());
    }

    @Override
    public String toString() {
        return String.format("Contact{id=%d, type=%s, user=%s}", id, type.getId(), user.getId());
    }
}
