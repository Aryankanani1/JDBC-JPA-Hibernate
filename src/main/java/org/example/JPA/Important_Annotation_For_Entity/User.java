package org.example.JPA.Important_Annotation_For_Entity;

import jakarta.persistence.*;

import java.util.Date;

// Distinct entity name + table so this demo entity doesn't clash with org.example.JPA.User.
@Entity(name = "AnnotatedUser")
@Table(name = "annotated_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name", nullable = false, length = 50)
    private String name;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Lob // field should be treated as the large data
    private byte[] profilePicture;
    @Transient
    private String temporaryData;

    public User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getTemporaryData() {
        return temporaryData;
    }

    public void setTemporaryData(String temporaryData) {
        this.temporaryData = temporaryData;
    }
}
enum Status{
    ACTIVE,
    INACTIVE
}
